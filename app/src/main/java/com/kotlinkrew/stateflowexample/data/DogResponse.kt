package com.kotlinkrew.stateflowexample.data

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class DogResponse(@SerializedName("message") val data: JsonObject)
data class DogImageResponse(@SerializedName("message") var result: JsonArray)