package com.testapps.nycschools.core

import retrofit2.Response
import timber.log.Timber
import java.io.IOException

/**
 * Holds success and Error State from network call
 */
sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(
        val exception: Exception,
        val errorCode: Int = 0
    ) : NetworkResult<Nothing>()
}

/**
 * SafeAPI call to parse network response
 * and wraps the response into [NetworkResult]
 */
suspend fun <T : Any> safeApiCall(
    errorMessage: String = "",
    retrofitCall: suspend () -> Response<T>
): NetworkResult<T> {
    return safeApiCallWrapper({
        val response = retrofitCall()
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null) {
            NetworkResult.Success(responseBody)
        } else {
            NetworkResult.Error(IOException("${response.errorBody()}"), response.code())
        }
    }, errorMessage)
}

private suspend fun <T : Any> safeApiCallWrapper(
    call: suspend () -> NetworkResult<T>,
    errorMessage: String
): NetworkResult<T> {
    return try {
        call()
    } catch (e: Exception) {
        Timber.e(e)
        // An exception was thrown when calling the API so we're converting this to an IOException
        NetworkResult.Error(IOException(errorMessage, e))
    }
}