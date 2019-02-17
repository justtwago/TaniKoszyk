package com.github.justtwago.usecases.usecases.auth

import com.github.justtwago.service.common.Response
import com.github.justtwago.service.firebase.FirebaseAuthenticator
import com.github.justtwago.usecases.model.Result
import com.github.justtwago.usecases.model.auth.AuthenticationRequest
import com.github.justtwago.usecases.usecases.base.AsyncUseCase


class SignUpUseCase(private val authenticator: FirebaseAuthenticator) : AsyncUseCase<AuthenticationRequest, Result<String>> {
    override suspend fun execute(request: AuthenticationRequest): Result<String> {
        val response = authenticator.signUp(request.email, request.password)
        return when (response) {
            is Response.Success.WithBody -> Result.Success(response.body)
            is Response.Failure -> Result.Failure(response.throwable.message.orEmpty())
            else -> Result.Failure()
        }
    }
}