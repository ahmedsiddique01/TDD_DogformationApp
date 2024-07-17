package com.tdd.dogformation.data.repository

import com.tdd.dogformation.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getDogBreeds(): Flow<List<DogBreed>>
    suspend fun updateDogBreeds(name: String, isFavourite: Boolean)
    fun getFavouriteDogBreeds(): Flow<List<DogBreed>>
}
