package com.example.brandat.utils


sealed class ResponseResult<S,E> {

    data class Success<S,E>(val  data:S) : ResponseResult<S, E>()
    data class Error<S,E>(val errorCode:E,val message:String?=null) :ResponseResult<S, E>()
}

