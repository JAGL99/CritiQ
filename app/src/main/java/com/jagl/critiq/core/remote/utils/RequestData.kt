package com.jagl.critiq.core.remote.utils

import com.google.gson.Gson
import com.jagl.critiq.core.model.ApiResult
import com.jagl.critiq.core.remote.model.MovieDbApiError
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object RequestData {

    suspend fun <T> safeCall(
        apiCall: suspend () -> T
    ): ApiResult<T> {
        return try {
            ApiResult.Success(apiCall())
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = errorBody?.let {
                Gson().fromJson(errorBody, MovieDbApiError::class.java)
            }
            ApiResult.Error(errorResponse?.statusMessage ?: e.message())
        } catch (e: Exception) {
            NETWORK_EXCEPTIONS.find { it::class.java == e::class.java }
                ?.let { networkException ->
                    ApiResult.Error(networkException.message!!)
                } ?: ApiResult.Error(e.message ?: DEFAULT_EXCEPTION_MESSAGE)
        }
    }

    val NETWORK_EXCEPTIONS = listOf(
        UnknownHostException("No internet connection"),
        SocketTimeoutException("Request timed out"),
        ConnectException("Connection error"),
    )

    val DEFAULT_EXCEPTION_MESSAGE = "Unknown network error"

}