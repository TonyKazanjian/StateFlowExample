package com.kotlinkrew.stateflowexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kotlinkrew.stateflowexample.network.DogRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DogRepository): ViewModel() {

    val channel = repository.channel

    fun fetchBreeds(charToSearch: Char) {
        viewModelScope.launch {
            repository.getBreeds(charToSearch, this)
        }
    }

    fun onDestroy(){
        channel.close()
    }
}