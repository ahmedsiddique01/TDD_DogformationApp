package com.tdd.dogformation.domain.usecase.favouriteDogBreeds

import com.tdd.dogformation.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow

interface UseCase {
    fun getFavouriteDogBreeds(): Flow<List<DogBreed>>
    suspend fun addToFavourites(name: String, isFavourite: Boolean)
}