package com.kotlinkrew.stateflowexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kotlinkrew.stateflowexample.network.DogRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DogRepository): ViewModel() {

    // Get reference to the repository's StateFlow

    fun fetchBreeds(charToSearch: Char) {
        viewModelScope.launch {
            repository.getBreeds(charToSearch)
        }
    }
}