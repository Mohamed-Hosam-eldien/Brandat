package com.example.brandat.utils


sealed class ResponseResult<T> {

    data class Success<S>(val  data:S) : ResponseResult<S>()
    data class Error<E>(val message:String?=null) :ResponseResult<E>()
}

