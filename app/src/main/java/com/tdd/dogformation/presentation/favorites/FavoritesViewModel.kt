package com.tdd.dogformation.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tdd.dogformation.domain.model.DogBreed
import com.tdd.dogformation.domain.usecase.favouriteDogBreeds.FavouriteDogBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val favouriteDogBreedsUseCase: FavouriteDogBreedsUseCase) :
    ViewModel() {

    val _favoriteDogBreeds = MutableStateFlow<List<DogBreed>>(emptyList())

    val favoriteDogBreeds = _favoriteDogBreeds.asStateFlow()

    init {
        getFavoriteDogBreeds()
    }

    private fun getFavoriteDogBreeds() {
        viewModelScope.launch {
            favouriteDogBreedsUseCase.getFavouriteDogBreeds().collect { dogBreeds ->
                _favoriteDogBreeds.value = dogBreeds
            }
        }
    }

    fun removeFromFavorites(dogBreed: DogBreed) {
        viewModelScope.launch {
            favouriteDogBreedsUseCase.addToFavourites(dogBreed.name, false)
            getFavoriteDogBreeds()
        }
    }
}