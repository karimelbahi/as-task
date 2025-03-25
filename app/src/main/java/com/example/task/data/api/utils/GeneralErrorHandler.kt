package com.example.task.data.api.utils

import com.example.task.R
import com.example.task.presentation.utils.ResourcesResolver
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeneralErrorHandler @Inject constructor(private val resourcesResolver: ResourcesResolver) {

    fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.Network
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_BAD_REQUEST -> ErrorEntity.BadRequest
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound
                    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable
                    //add any other error type here
                    // all the others will be treated as unknown error
                    else -> ErrorEntity.Unknown
                }
            }
            else -> ErrorEntity.Unknown
        }
    }

    fun getErrorMessage(errorEntity: ErrorEntity) = when (errorEntity) {
        ErrorEntity.Network -> resourcesResolver.getString(R.string.msg_network_error)
        ErrorEntity.BadRequest -> resourcesResolver.getString(R.string.bad_request)
        ErrorEntity.NotFound -> resourcesResolver.getString(R.string.msg_not_found_error)
        ErrorEntity.ServiceUnavailable -> resourcesResolver.getString(R.string.msg_service_unavailable)
        ErrorEntity.Unknown -> resourcesResolver.getString(R.string.msg_unknown_error)
    }
}