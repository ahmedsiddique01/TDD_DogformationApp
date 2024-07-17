package com.tdd.dogformation.usecases

import com.tdd.dogformation.CoroutineTestRule
import com.tdd.dogformation.data.repository.DataRepository
import com.tdd.dogformation.domain.model.DogBreed
import com.tdd.dogformation.domain.usecase.favouriteDogBreeds.FavouriteDogBreedsUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
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
class FavouriteDogBreedsUseCaseTest {
    private lateinit var favouriteDogBreedsUseCase: FavouriteDogBreedsUseCase

    private val dataRepository = mockk<DataRepository>()


    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        favouriteDogBreedsUseCase = FavouriteDogBreedsUseCase(dataRepository)
    }

    @Test
    fun `fetch dog breeds`() = runTest {
        // given
        val mockFavouriteDogBreeds = mockk<List<DogBreed>>(relaxed = true)

        every {
            dataRepository.getFavouriteDogBreeds()
        } returns flow { emit(mockFavouriteDogBreeds) }

        // when
        val getFavouriteDogBreedsUseCaseValue =
            favouriteDogBreedsUseCase.getFavouriteDogBreeds()

        //then
        getFavouriteDogBreedsUseCaseValue.first() shouldBe mockFavouriteDogBreeds
    }

    @Test
    fun `add to favourites`() = runTest {
        // given
        val name = "african"
        val isFavourite = true

        coEvery {
            dataRepository.updateDogBreeds(name, isFavourite)
        } returns Unit

        // when
        val getFavouriteDogBreedsUseCaseValue =
            favouriteDogBreedsUseCase.addToFavourites(name, isFavourite)

        //then
        getFavouriteDogBreedsUseCaseValue shouldBe Unit
    }
}