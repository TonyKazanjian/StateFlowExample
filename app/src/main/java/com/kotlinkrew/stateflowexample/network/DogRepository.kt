package com.kotlinkrew.stateflowexample.network

import android.util.Log
import com.kotlinkrew.stateflowexample.domain.usecase.GetAllBreeds
import com.kotlinkrew.stateflowexample.domain.usecase.GetBreedImages
import com.kotlinkrew.stateflowexample.domain.usecase.UseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogRepository {
    private val TAG: String = DogRepository::class.java.simpleName
    private val api: Api
    private val getAllBreeds: GetAllBreeds
    private val getBreedImages: GetBreedImages

    init {
        api = createClient()
        getAllBreeds = GetAllBreeds(api)
        getBreedImages = GetBreedImages(api)
    }

    suspend fun getBreeds(){
        getAllBreeds(UseCase.None()){
            it.handleResult(
                {success -> Log.d(TAG, "${success.data}")},
                {error -> Log.d(TAG, "${error.message}")},
                {Log.d(TAG, "is loading")}
            )
        }
    }

    suspend fun getBreedImage(){
        getBreedImages(GetBreedImages.Params("pug")) {
            it.handleResult(
                {success -> Log.d(TAG, "${success.result}")},
                {error -> Log.d(TAG, "${error.message}")},
                {Log.d(TAG, "is loading")}
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
