package com.tanikoszyk.usecases.usecases.auth

import com.tanikoszyk.service.firebase.FirebaseAuthenticator
import com.tanikoszyk.usecases.usecases.base.AsyncArgumentlessUseCase


class CheckIsUserSignInUseCase(private val authenticator: FirebaseAuthenticator) : AsyncArgumentlessUseCase<Boolean> {
    override suspend fun execute(): Boolean {
        return authenticator.isUserLoggedIn()
    }
}