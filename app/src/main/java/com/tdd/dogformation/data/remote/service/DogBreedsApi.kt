package com.tdd.dogformation.data.remote.service

import com.tdd.dogformation.data.remote.dto.DogBreedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogBreedsApi {
    @GET("breeds/list/all")
    suspend fun fetchDogBreeds(): Response<DogBreedResponse>
}
