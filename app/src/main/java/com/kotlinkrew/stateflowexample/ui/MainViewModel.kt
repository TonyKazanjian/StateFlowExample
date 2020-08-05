package com.kotlinkrew.stateflowexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinkrew.stateflowexample.network.DogRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DogRepository): ViewModel() {

    val mainStateLiveData = repository.mainStateLiveData

    fun fetchBreeds(charToSearch: Char) {
        viewModelScope.launch {
            repository.getBreeds(charToSearch)
        }
    }
}