package com.kotlinkrew.stateflowexample.network

import com.google.gson.JsonObject
import com.kotlinkrew.stateflowexample.domain.model.DogBreed
import com.kotlinkrew.stateflowexample.domain.usecase.GetBreedImages
import kotlinx.coroutines.flow.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogRepository {
    private val api: Api
    private val getBreedImages: GetBreedImages

    init {
        api = createClient()
        getBreedImages = GetBreedImages(api)
    }

    /**
     * Mutable and Immutable StateFlow fields
     */


    private val breedsList = mutableListOf<DogBreed>()

    suspend fun getBreeds(charToFilter: Char = "a"[0]){
        breedsList.clear()
        try {
            // Create a flow that produces the list of DogBreed objects
            val dogFlow = flowOf(createBreedsFromJson(api.getAllDogBreeds().data, charToFilter))

            // 1. Switch to next flow to get images when the list value is emitted
            // 2. Create flows for getting breed images
            // 3. Merge the list of flows to get images into a single flow (note: This does not preserve order)
            // 4. Collect and publish flow states


        } catch (e: Exception){
            // Set StateFlow to error state
        }
    }

    private fun createBreedsFromJson(response: JsonObject, charToFilter: Char): List<DogBreed>{
        val breeds = mutableListOf<DogBreed>()
        response.entrySet().filter { it.key[0] == charToFilter.toLowerCase() }.map {
            breeds.add(DogBreed(it.key))
        }
        return breeds
    }

    /**
     * Creates list of flows for merging
     */
    private fun createBreedImageFlows(breeds: List<DogBreed>): List<Flow<Unit>> {
        return breeds.map {
            getBreedImage(it)
        }
    }

    // Return the call to getBreedImages as a Flow
    private fun getBreedImage(breed: DogBreed): Flow<Unit> {
        return flow {

        }
    }

    private fun createClient(): Api {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(Api::class.java)
    }
}
