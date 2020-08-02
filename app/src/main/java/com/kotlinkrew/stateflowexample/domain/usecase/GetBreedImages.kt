package com.kotlinkrew.stateflowexample.domain.usecase

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.kotlinkrew.stateflowexample.data.DogImageResponse
import com.kotlinkrew.stateflowexample.network.Api

class GetBreedImages(private val api: Api): UseCase<List<String>, GetBreedImages.Params>() {

    data class Params(val breed: String)

    override suspend fun run(params: Params): List<String> {
        val response = api.getBreedImages(params.breed)
        return createImagesFromJson(response.result)
    }

    private fun createImagesFromJson(array: JsonArray): List<String>{
        return Gson().fromJson(array, Array<String>::class.java).toList()
    }
}