package com.github.justtwago.usecases.usecases.auth

import com.github.justtwago.service.firebase.FirebaseAuthenticator
import com.github.justtwago.usecases.usecases.base.AsyncArgumentlessUseCase


class CheckIsUserSignInUseCase(private val authenticator: FirebaseAuthenticator) : AsyncArgumentlessUseCase<Boolean> {
    override suspend fun execute(): Boolean {
        return authenticator.isUserLoggedIn()
    }
}