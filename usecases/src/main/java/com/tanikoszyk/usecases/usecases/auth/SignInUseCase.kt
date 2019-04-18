package com.tanikoszyk.usecases.usecases.auth

import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.firebase.FirebaseAuthenticator
import com.tanikoszyk.usecases.model.Result
import com.tanikoszyk.usecases.model.auth.AuthenticationRequest
import com.tanikoszyk.usecases.usecases.base.AsyncUseCase

class SignInUseCase(private val authenticator: FirebaseAuthenticator) : AsyncUseCase<AuthenticationRequest, Result<String>> {
    override suspend fun execute(request: AuthenticationRequest): Result<String> {
        val response = authenticator.signIn(request.email, request.password)
        return when (response) {
            is Response.Success.WithBody -> Result.Success(response.body)
            is Response.Failure -> Result.Failure(response.throwable.message.orEmpty())
            else -> Result.Failure()
        }
    }
}