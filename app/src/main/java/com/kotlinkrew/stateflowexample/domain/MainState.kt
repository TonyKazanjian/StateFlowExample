package com.kotlinkrew.stateflowexample.domain

import com.kotlinkrew.stateflowexample.domain.model.DogBreed

data class MainState (
    val loading: Boolean = false,
    val result: List<DogBreed> = emptyList(),
    val error: String = ""
)