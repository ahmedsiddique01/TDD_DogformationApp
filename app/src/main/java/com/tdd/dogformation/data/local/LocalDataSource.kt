package com.tdd.dogformation.data.local

import com.tdd.dogformation.data.Resource
import com.tdd.dogformation.data.local.persistence.DogBreedsDao
import com.tdd.dogformation.domain.model.DogBreed
import com.tdd.dogformation.domain.model.DogBreedImages
import com.tdd.dogformation.utils.Converters
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LocalDataSource @Inject
constructor(private val dao: DogBreedsDao) : LocalSource {

    override fun getDogBreeds(): Flow<List<DogBreed>> {
        return dao.getDogBreeds()
    }

    override suspend fun storeDogBreedListInDb(dogBreeds: List<DogBreed>) {
        dao.insertAllDogBreeds(dogBreeds)
    }

    override suspend fun getDogBreedImages(breedName: String): Resource<List<String>> {
        val dogBreedImageList = dao.getDogBreedImages(breedName)
        return if (dogBreedImageList.isEmpty()) {
            Resource.DataError(errorCode = 2)
        } else {
            val converter = Converters()
            Resource.Success(converter.fromString(dogBreedImageList[0]))
        }
    }

    override suspend fun storeDogBreedImageListInDb(breedImages: DogBreedImages) {
        dao.insertDogBreedImages(breedImages)
    }

    override suspend fun updateDogBreeds(name: String, isFavourite: Boolean) {
        dao.updateDogBreeds(name, isFavourite)
    }

    override fun getFavouriteDogBreeds(): Flow<List<DogBreed>> {
        return dao.getFavouriteDogBreeds()
    }
}
