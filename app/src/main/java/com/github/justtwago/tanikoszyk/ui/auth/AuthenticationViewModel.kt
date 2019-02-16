package com.github.justtwago.tanikoszyk.ui.auth

import androidx.lifecycle.ViewModel
import com.github.justtwago.service.common.Result
import com.github.justtwago.service.firebase.FirebaseAuthenticator


class AuthenticationViewModel(private val authenticator: FirebaseAuthenticator) : ViewModel() {
    suspend fun signIn(login: String, password: String): Boolean {
        val result = authenticator.signUp(login, password)
        return result is Result.Success
    }
}
