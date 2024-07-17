package com.tdd.dogformation.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tdd.dogformation.domain.model.DogBreed
import com.tdd.dogformation.domain.model.DogsResponse
import com.tdd.dogformation.domain.usecase.dogBreeds.DogBreedsUseCase
import com.tdd.dogformation.domain.usecase.favouriteDogBreeds.FavouriteDogBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dogBreedsUseCase: DogBreedsUseCase,
    private val favouriteDogBreedsUseCase: FavouriteDogBreedsUseCase
) : ViewModel() {

    var query = ""
    private val _uiState = MutableStateFlow(DogsResponse(isLoading = true))
    val uiState: Flow<DogsResponse> = _uiState.asStateFlow()

    fun getDogBreedsList() {
        viewModelScope.launch {
            dogBreedsUseCase.getDogBreeds()
                .onStart { _uiState.value = _uiState.value.copy(isLoading = true) }
                .map { result ->
                    val dogBreeds = if (result.isNotEmpty()) {
                        result.map {
                            DogBreed(
                                name = it.name,
                                subBreeds = it.subBreeds, imageUrl = it.imageUrl,
                                isFavourite = it.isFavourite
                            )
                        }
                    } else {
                        emptyList()
                    }
                    val filteredBreeds = if (query.isNotEmpty()) {
                        dogBreeds.filter { dogBreed ->
                            dogBreed.name.contains(query, ignoreCase = true)
                        }
                    } else {
                        dogBreeds
                    }

                    _uiState.value.copy(isLoading = false, dogBreeds = filteredBreeds)
                }
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = e.message
                    )
                }
                .collect { newState ->
                    _uiState.value = newState
                }
        }
    }

    fun makeFavoriteAndGetList(query: String, dogName: String, isFavourite: Boolean) {
        viewModelScope.launch {
            favouriteDogBreedsUseCase.addToFavourites(name = dogName, isFavourite = isFavourite)
        }
        getDogBreedsList()
    }
}