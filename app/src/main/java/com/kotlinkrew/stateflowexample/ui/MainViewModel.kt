package com.kotlinkrew.stateflowexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kotlinkrew.stateflowexample.network.DogRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DogRepository): ViewModel() {

    val stateLiveData = repository.mainStateFlow.asLiveData()

    fun fetchBreeds() {
        viewModelScope.launch {
            repository.getBreeds("c"[0])
        }
    }
}