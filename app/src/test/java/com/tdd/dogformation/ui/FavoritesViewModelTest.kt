package com.tdd.dogformation.ui

import com.tdd.dogformation.CoroutineTestRule
import com.tdd.dogformation.domain.model.DogBreed
import com.tdd.dogformation.domain.usecase.favouriteDogBreeds.FavouriteDogBreedsUseCase
import com.tdd.dogformation.presentation.favorites.FavoritesViewModel
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class FavoritesViewModelTest {

    private lateinit var viewModel: FavoritesViewModel

    private val favouriteDogBreedsUseCase = mockk<FavouriteDogBreedsUseCase>()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {

    }

    @Test
    fun `initialize then fetch favourite dog breeds`() = runTest {

        val breed = DogBreed(
            name = "poodle",
            subBreeds = "",
            isFavourite = true,
            imageUrl = "dummy_url"
        )

        every { favouriteDogBreedsUseCase.getFavouriteDogBreeds() } returns flow {
            emit(listOf(breed, breed))
        }

        viewModel = FavoritesViewModel(favouriteDogBreedsUseCase)

        viewModel.favoriteDogBreeds.first().apply {
            size shouldBe 2
            first().name shouldBe "poodle"
        }
    }

    @Test
    fun `initialize then fetch favourite dog breeds which has no items`() = runTest {
        every { favouriteDogBreedsUseCase.getFavouriteDogBreeds() } returns flow {
            emit(emptyList())
        }

        viewModel = FavoritesViewModel(favouriteDogBreedsUseCase)

        viewModel.favoriteDogBreeds.first().apply {
            size shouldBe 0
        }
    }
}