package com.tdd.dogformation.domain.usecase.dogBreeds

import com.tdd.dogformation.data.repository.DataSource
import com.tdd.dogformation.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DogBreedsUseCase @Inject constructor(
    private val dataRepository: DataSource
) : UseCase {
     override fun getDogBreeds(): Flow<List<DogBreed>> {
        return dataRepository.getDogBreeds()
    }
}