package com.csdev.baseappskeleton.utils

import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

fun <T> Exception.setError(): Resource.Error<T> {
    when (this) {
        is HttpException -> {
            val errorBody = this.response()?.errorBody()
            val errorBodyStr = this.response()?.errorBody()?.string()
//            try {
//                val resp = Gson()
//                    .fromJson(
//                        errorBody?.charStream(),
//                        BaseResponse::class.java
//                    )
//                return Resource.Error(
//                    message = resp.status?.message ?: "An unexpected error occurred",
//                    code = this.code()
//                )
//            } catch (_: Exception) {
//
//            }
            return Resource.Error(message = this.message ?: "An unexpected error occurred", code = this.code())
        }
        is IOException -> {
            return Resource.Error("Couldn't reach server. Check your internet connection.")
        }
        else -> {
            return Resource.Error("An unexpected error occurred")
        }
    }
}