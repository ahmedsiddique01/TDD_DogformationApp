package com.tdd.dogformation.data.repository

import com.tdd.dogformation.data.local.LocalSource
import com.tdd.dogformation.data.remote.RemoteSource
import com.tdd.dogformation.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataRepository @Inject
constructor(
    private val remoteDataSource: RemoteSource,
    private val localDataSource: LocalSource
) : DataSource {

    override fun getDogBreeds(): Flow<List<DogBreed>> {
        return localDataSource.getDogBreeds().map {
            if (it.isEmpty()) {
                remoteDataSource.getDogBreeds().data?.let { list ->
                    localDataSource.storeDogBreedListInDb(list)
                }
            }
            it
        }.catch {
            emitAll(flowOf(emptyList()))
        }
    }

    override suspend fun updateDogBreeds(name: String, isFavourite: Boolean) {
        localDataSource.updateDogBreeds(name, isFavourite)
    }

    override fun getFavouriteDogBreeds(): Flow<List<DogBreed>> {
        return localDataSource.getFavouriteDogBreeds()
    }
}
