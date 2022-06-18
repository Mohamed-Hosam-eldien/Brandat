package com.example.brandat.utils


sealed class NetworkResult<T> {

    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error<T>(val exception: Exception) : NetworkResult<T>()
    object Loading : NetworkResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<T> -> "Success[data=$data]"
            is Error<T> -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}