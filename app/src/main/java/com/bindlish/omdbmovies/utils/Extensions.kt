package com.bindlish.omdbmovies.utils

import com.bindlish.omdbmovies.data.network.Resource
import retrofit2.Response
import java.util.*

/**
 * Converts Retrofit [Response] to [Resource] which provides state
 * and data to the UI.
 */
fun <ResultType> Response<ResultType>.toResource(): Resource<ResultType> {
    val error = errorBody()?.toString() ?: message()
    return when {
        isSuccessful -> {
            val body = body()
            when {
                body != null -> Resource.success(body)
                else -> Resource.error(error)
            }
        }
        else -> Resource.error(error)
    }
}

fun String.firstToCapital() : String {
    return substring(0,1).toUpperCase(Locale.ENGLISH).plus(substring(1))
}