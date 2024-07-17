package com.tdd.dogformation.data.remote

import com.tdd.dogformation.data.Resource
import com.tdd.dogformation.domain.model.DogBreed

interface RemoteSource {
    suspend fun getDogBreeds(): Resource<List<DogBreed>>
}

