package com.kotlinkrew.stateflowexample.domain.usecase

import com.kotlinkrew.stateflowexample.data.DogImageResponse
import com.kotlinkrew.stateflowexample.network.Api

class GetBreedImages(private val api: Api): UseCase<DogImageResponse, GetBreedImages.Params>() {

    data class Params(val breed: String)

    override suspend fun run(params: Params): DogImageResponse {
        return api.getBreedImages(params.breed)
    }
}