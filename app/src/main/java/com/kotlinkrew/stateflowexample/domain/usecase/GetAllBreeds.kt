package com.kotlinkrew.stateflowexample.domain.usecase

import com.kotlinkrew.stateflowexample.data.DogResponse
import com.kotlinkrew.stateflowexample.network.Api

class GetAllBreeds(private val api: Api): UseCase<DogResponse, UseCase.None>() {
    override suspend fun run(params: None): DogResponse {
        return api.getAllDogBreeds()
    }
}