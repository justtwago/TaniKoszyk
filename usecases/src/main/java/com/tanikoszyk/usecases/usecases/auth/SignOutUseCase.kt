package com.tanikoszyk.usecases.usecases.auth

import com.tanikoszyk.service.firebase.FirebaseAuthenticator
import com.tanikoszyk.usecases.usecases.base.AsyncArgumentlessUseCase


class SignOutUseCase(private val authenticator: FirebaseAuthenticator) : AsyncArgumentlessUseCase<Unit> {
    override suspend fun execute() {
        authenticator.signOut()
    }
}