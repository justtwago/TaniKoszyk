package com.tanikoszyk.usecases.usecases.base


interface UseCase<in R, out S> {
    fun execute(request: R): S?
}

interface ArgumentlessUseCase<out S> {
    fun execute(): S?
}

interface AsyncArgumentlessUseCase<out S> {
    suspend fun execute(): S?
}

interface AsyncUseCase<in R, out S> {
    suspend fun execute(request: R): S?
}