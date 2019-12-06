package com.tanikoszyk.usecases.model.auth


data class AuthenticationRequest(
        val email: String,
        val password: String
) {
    val isComplete: Boolean
        get() = email.isNotEmpty() && password.isNotEmpty()
}