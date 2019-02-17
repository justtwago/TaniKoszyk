package com.github.justtwago.usecases.usecases.auth

import com.github.justtwago.service.firebase.FirebaseAuthenticator
import com.github.justtwago.usecases.usecases.base.AsyncArgumentlessUseCase


class SignOutUseCase(private val authenticator: FirebaseAuthenticator) : AsyncArgumentlessUseCase<Unit> {
    override suspend fun execute() {
        authenticator.signOut()
    }
}