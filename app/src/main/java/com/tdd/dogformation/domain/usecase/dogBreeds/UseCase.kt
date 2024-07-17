package com.tdd.dogformation.domain.usecase.dogBreeds

import com.tdd.dogformation.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow

interface UseCase {
    fun getDogBreeds(): Flow<List<DogBreed>>
}