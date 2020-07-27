package com.kotlinkrew.stateflowexample.network

import com.kotlinkrew.stateflowexample.data.DogImageResponse
import com.kotlinkrew.stateflowexample.data.DogResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET(value = "breeds/list/all")
    suspend fun getAllDogBreeds(): DogResponse

    @GET(value = "breed/{breed}/images")
    suspend fun getBreedImages(@Path("breed") breed: String): DogImageResponse
}