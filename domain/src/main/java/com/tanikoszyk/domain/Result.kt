package com.tanikoszyk.domain

sealed class Result<T> {
    sealed class Success<T> : Result<T>() {
        data class WithBody<T>(val body: T) : Success<T>()
        class Empty<T> : Success<T>()
    }

    data class Failure<T>(val throwable: Throwable) : Result<T>()
}
