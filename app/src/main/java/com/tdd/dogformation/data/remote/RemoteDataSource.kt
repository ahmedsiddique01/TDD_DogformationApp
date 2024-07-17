package com.tdd.dogformation.data.remote

import android.util.Log
import com.tdd.dogformation.data.Resource
import com.tdd.dogformation.data.remote.dto.DogBreedResponse
import com.tdd.dogformation.data.remote.helper.DogBreedWithSubBreeds
import com.tdd.dogformation.data.remote.service.DogBreedsApi
import com.tdd.dogformation.domain.model.DogBreed
import com.tdd.dogformation.utils.Constants.COMMA
import javax.inject.Inject

class RemoteDataSource @Inject
constructor(private val api: DogBreedsApi) : RemoteSource {

    override suspend fun getDogBreeds(): Resource<List<DogBreed>> {
        try {
            val dogBreedList = mutableListOf<DogBreed>()
            val res = api.fetchDogBreeds()
            val dogBreedNameWithSubBreedList = mutableListOf<DogBreedWithSubBreeds>()

            when (res.isSuccessful) {
                true -> {
                    res.body()?.let { body ->
                        if (body.status == DogBreedResponse.SUCCESS_STATUS) {
                            body.message.entries.forEach {
                                dogBreedNameWithSubBreedList.add(
                                    DogBreedWithSubBreeds(
                                        it.key,
                                        it.value.joinToString(COMMA) //Joining all the subbreeds by comma
                                    )
                                )
                            }
                            return Resource.Success(data = dogBreedList)
                        } else return Resource.DataError(errorCode = body.code)
                    } ?: return Resource.DataError(errorCode = res.code())
                }
                false -> return Resource.DataError(errorCode = res.code())
            }
        } catch (e: Exception) {
            Log.e("NETWORK_API_ERROR", "List cannot load ${e.hashCode()}")
            return Resource.DataError(errorCode = e.hashCode())
        }
    }

}
