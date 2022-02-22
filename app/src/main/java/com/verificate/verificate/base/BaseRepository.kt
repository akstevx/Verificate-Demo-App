package com.verificate.verificate.base

import com.google.gson.Gson
import com.verificate.verificate.util.UseCaseResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

open class BaseRepository {

    suspend fun <R:Any,T:Any> safeNewApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean,
                                             onSuccessOperations:suspend (request:R,response:T? )->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(request,response)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }

    suspend fun <R:Any,T:Any> safeRequestApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean,
                                             onSuccessOperations:suspend (request:R )->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(request)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }

    suspend fun <R:Any,T:Any> safeRequestApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean,
                                                 onSuccessOperations:suspend (request:R , response:T)->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(request, response)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }

    suspend fun <T:Any> safeGetApiCall(apiCall: () ->Deferred<T>, checkIfSuccessful:(T)->Boolean):UseCaseResult<T>{
        return try {
            val response = apiCall().await()
            if(checkIfSuccessful(response)){
                try {
//                    onSuccessOperations(response)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }

    suspend inline fun <R:Any, reified T:Any> safeApiCall(request: R, apiCall: (request:R) ->
    Deferred<T>, checkIfSuccessful:(T)->Boolean): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        } catch (ex:Exception){
            when(ex){
                is HttpException -> {
                   try {
                       Timber.e(ex)
                       val body = ex.response()?.errorBody()
                       val errorJson = Gson().fromJson(body?.string() ?: "", T::class.java)
                       UseCaseResult.FailedAPI(errorJson)
                   }catch (ex:java.lang.Exception){
                       Timber.e(ex)
                       UseCaseResult.Error(ex)
                   }
                }
                else -> {
                    Timber.e(ex)
                    UseCaseResult.Error(ex)
                }
            }

        }
    }
    suspend fun <T:Any> safeGetApiCallAndNormalOperation(apiCall: () ->Deferred<T>, checkIfSuccessful:(T)->Boolean,onSuccessOperations:(response:T)->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall().await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(response)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }

    suspend fun <R:Any,T:Any> safeApiCallUpdate(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean,
                                                onSuccessOperations:suspend (response:T, request: R) ->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(response, request)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }

    suspend fun <R:Any,T:Any> safeApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean,onSuccessOperations:(response:T)->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(response)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }
    suspend fun <R:Any,T:Any> safeApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean, onSuccessOperations:suspend (response:T)->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(response)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }
    suspend fun <R:Any,T:Any> safeApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean, onSuccessOperations:suspend (request:R,response:T)->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(request,response)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }
    suspend fun <R:Any,T:Any> safeWorkerApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean):Boolean{
        return try {
            val response = apiCall(request).await()
            checkIfSuccessful(response)
        }catch (ex:Exception){
            Timber.e(ex)
            false
        }
    }
    suspend fun <R:Any,T:Any> safeWorkerApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean,onSuccessOperations:(response:T)->Unit):Boolean{
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                onSuccessOperations(response)
                true
            }else{
                false
            }
        }catch (ex:Exception){
            Timber.e(ex)
            false
        }
    }
    suspend fun <R:Any,T:Any> safeWorkerApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean,onSuccessOperations:suspend (response:T)->Unit):Boolean{
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(response)
                }catch (ex:java.lang.Exception){
                    Timber.e(ex)
                }

                true
            }else{
                false
            }
        }catch (ex:Exception){
            Timber.e(ex)
            false
        }
    }

}
suspend fun <R:Any,T:Any> safeApiCall2(request: R, apiCall: (request:R) -> Deferred<T>, checkIfSuccessful:(T)->Boolean,checkIfSuccessful2:(T)->Boolean): UseCaseResult<T> {
    return try {
        val response = apiCall(request).await()
        when {
            checkIfSuccessful(response) -> {
                UseCaseResult.Success(response)
            }
            checkIfSuccessful2(response) -> {
                UseCaseResult.Success(response)
            }
            else -> {
                UseCaseResult.FailedAPI(response)
            }
        }
    }catch (ex:Exception){
        Timber.e(ex)
        UseCaseResult.Error(ex)
    }
}