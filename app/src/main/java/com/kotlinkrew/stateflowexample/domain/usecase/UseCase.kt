package com.kotlinkrew.stateflowexample.domain.usecase

import com.kotlinkrew.stateflowexample.domain.Result
import java.lang.Exception

abstract class UseCase<out Type, in Params> where Type: Any {

    suspend operator fun invoke(params: Params, onResult: (Result<Type>) -> Unit = {}){
        onResult(Result.InProgress)
        try{
            val response = run(params)
            onResult(
                Result.Success(
                    response
                )
            )
        } catch (e: Exception){
            onResult(Result.Error(e))
        }
    }

    abstract suspend fun run(params: Params): Type

    class None
}