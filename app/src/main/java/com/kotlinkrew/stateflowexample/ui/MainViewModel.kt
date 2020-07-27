package com.kotlinkrew.stateflowexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinkrew.stateflowexample.network.DogRepository
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    fun fetchBreeds(repository: DogRepository) {
        viewModelScope.launch {
            repository.getBreeds()
            repository.getBreedImage()
        }
    }
}