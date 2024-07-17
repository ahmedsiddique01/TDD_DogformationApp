package com.tdd.dogformation.ui

import com.tdd.dogformation.CoroutineTestRule
import com.tdd.dogformation.domain.model.DogBreed
import com.tdd.dogformation.domain.usecase.dogBreeds.DogBreedsUseCase
import com.tdd.dogformation.domain.usecase.favouriteDogBreeds.FavouriteDogBreedsUseCase
import com.tdd.dogformation.presentation.home.HomeViewModel
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    private val dogBreedsUseCase = mockk<DogBreedsUseCase>()
    private val favouriteDogBreedsUseCase = mockk<FavouriteDogBreedsUseCase>()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
    }

    @Test
    fun `initialize then fetch dog breeds succeeded`() = runTest {
        val breed = DogBreed(
            name = "poodle",
            subBreeds = "",
            isFavourite = false,
            imageUrl = "dummy_url"
        )
        every { dogBreedsUseCase.getDogBreeds() } returns flow {
            emit(listOf(breed, breed))
        }
        viewModel = HomeViewModel(dogBreedsUseCase, favouriteDogBreedsUseCase)
        viewModel.getDogBreedsList()
        advanceUntilIdle()
        val uiState = viewModel.uiState.first()
        uiState.isLoading shouldBe false
        uiState.dogBreeds?.size shouldBe 2
        uiState.dogBreeds?.first()?.name shouldBe "poodle"


    }

    @Test
    fun `initialize then fetch dog breeds failed`() = runTest {
        every { dogBreedsUseCase.getDogBreeds() } returns flow {
            emit(emptyList())
        }
        viewModel = HomeViewModel(dogBreedsUseCase, favouriteDogBreedsUseCase)
        viewModel.getDogBreedsList()
        advanceUntilIdle()
        val uiState = viewModel.uiState.first()
        uiState.isLoading shouldBe false
        uiState.dogBreeds?.size shouldBe 0

    }
}