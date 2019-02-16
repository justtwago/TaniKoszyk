package com.github.justtwago.service.common

@Suppress("UNCHECKED_CAST")
sealed class Result<T> where T : Any {
    open class Success<T : Any>(val result: T = Any() as T) : Result<T>()
    open class Failure<T : Any>(val result: T = Any() as T) : Result<T>()
    open class ServerFailure<T : Any> : Result<T>()
}