package com.kotlinkrew.stateflowexample.domain

import com.kotlinkrew.stateflowexample.domain.model.DogBreed

data class MainState (
    val loading: Boolean = false,
    val error: String? = "",
    val result: List<DogBreed> = emptyList()
)