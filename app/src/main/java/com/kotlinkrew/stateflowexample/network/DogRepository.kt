package com.kotlinkrew.stateflowexample.network

import com.google.gson.JsonObject
import com.kotlinkrew.stateflowexample.domain.MainState
import com.kotlinkrew.stateflowexample.domain.model.DogBreed
import com.kotlinkrew.stateflowexample.domain.usecase.GetBreedImages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogRepository {
    private val api: Api
    private val getBreedImages: GetBreedImages

    init {
        api = createClient()
        getBreedImages = GetBreedImages(api)
    }

    val channel = ConflatedBroadcastChannel<MainState>()

    private val breedsList = mutableListOf<DogBreed>()

    suspend fun getBreeds(charToFilter: Char = "a"[0]){
        breedsList.clear()
        try {
            val dogFlow = channelFlow { send(createBreedsFromJson(api.getAllDogBreeds().data, charToFilter)) }
            dogFlow.flatMapLatest {
                merge(*createBreedImageFlows(it).toTypedArray())
                    .onStart { channel.send(MainState(true)) }
                    .onCompletion {
                        channel.send(MainState(false, breedsList))
                    }
            }.collect()
        } catch (e: Exception){
            channel.send(MainState(false, emptyList(), "There was an error in loading this breed"))
        }
    }


    private fun createBreedsFromJson(response: JsonObject, charToFilter: Char): List<DogBreed>{
        val breeds = mutableListOf<DogBreed>()
        response.entrySet().filter { it.key[0] == charToFilter }.map {
            breeds.add(DogBreed(it.key))
        }
        return breeds
    }

    private fun createBreedImageFlows(breeds: List<DogBreed>): List<Flow<Unit>> {
        return breeds.map {
            getBreedImage(it)
        }
    }

    private fun getBreedImage(breed: DogBreed): Flow<Unit> {
        return flow {
            emit(
                getBreedImages(GetBreedImages.Params(breed.name)) {
                    it.handleResult(
                        {images ->
                            // Limit list size to 10 since response isn't paginated
                            val imageList = MutableList(10) { index -> images[index]}
                            breedsList.add(breed.copy(images = imageList))
                        }
                    )
                }
            )
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
