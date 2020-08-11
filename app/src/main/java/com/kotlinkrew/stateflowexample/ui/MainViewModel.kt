package com.kotlinkrew.stateflowexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinkrew.stateflowexample.network.DogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DogRepository): ViewModel() {

    val mainStateFlow = repository.mainStateFlow
    val searchCharFlow = MutableStateFlow("a"[0])

    init {
        viewModelScope.launch {
            searchCharFlow.collectLatest {
                repository.getBreeds(it)
            }
        }
    }
}