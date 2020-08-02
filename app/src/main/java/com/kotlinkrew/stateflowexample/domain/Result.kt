package com.kotlinkrew.stateflowexample.domain

sealed class Result<out V: Any> {
    data class Success<out V: Any>(val data: V) : Result<V>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object InProgress : Result<Nothing>()

    fun handleResult(result: (V) -> Unit, err: (Exception) -> Unit = {}, progress: () -> Unit = {}){
        when (this) {
            is Error -> {
                err(exception)
            }
            is Success -> {
                result(data)
            }
            is InProgress -> progress()
        }
    }
}