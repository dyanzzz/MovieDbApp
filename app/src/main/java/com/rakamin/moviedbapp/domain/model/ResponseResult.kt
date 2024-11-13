package com.rakamin.moviedbapp.domain.model

sealed class ResponseResult<out R> {
    object Loading : ResponseResult<Nothing>()
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Error(val throwable: Throwable) : ResponseResult<Nothing>()
}
