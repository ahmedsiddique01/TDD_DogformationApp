package com.tdd.dogformation.domain.model

data class DogsResponse(
    var dogBreeds: List<DogBreed>? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)