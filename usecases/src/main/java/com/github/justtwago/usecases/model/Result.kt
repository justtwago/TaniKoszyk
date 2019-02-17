package com.github.justtwago.usecases.model

@Suppress("UNCHECKED_CAST")
sealed class Result<T> where T : Any {
    open class Success<T : Any>(val result: T = Any() as T) : Result<T>()
    open class Failure<T : Any>(val result: T = Any() as T) : Result<T>()
}

typealias EmptyResult = Result<Any>