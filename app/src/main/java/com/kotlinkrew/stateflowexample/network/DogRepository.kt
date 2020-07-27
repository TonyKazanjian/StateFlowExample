package com.kotlinkrew.stateflowexample.network

import android.util.Log
import com.google.gson.JsonObject
import com.kotlinkrew.stateflowexample.domain.model.DogBreed
import com.kotlinkrew.stateflowexample.domain.usecase.GetBreedImages
import kotlinx.coroutines.flow.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogRepository {
    private val TAG: String = DogRepository::class.java.simpleName
    private val api: Api
    private val getBreedImages: GetBreedImages

    init {
        api = createClient()
        getBreedImages = GetBreedImages(api)
    }

    private val breedsList = mutableListOf<DogBreed>()

    suspend fun getBreeds(){
        try {
            val dogFlow = flowOf(createBreedsFromJson(api.getAllDogBreeds().data))
            dogFlow.flatMapLatest {
                merge(*createBreedImageFlows(it).toTypedArray())
                    .onStart {  }
                    .catch {

                    }
                    .onCompletion {

                    }
            }.collect()
        } catch (e: Exception){
            Log.d(TAG, "${e.message}")

        }
    }


    private fun createBreedsFromJson(response: JsonObject): List<DogBreed>{
        val breeds = mutableListOf<DogBreed>()
        response.entrySet().map {
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
                        },
                        {error -> Log.d(TAG, "${error.message}")},
                        {Log.d(TAG, "is loading")}
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
