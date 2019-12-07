package com.tanikoszyk.usecases.requests

data class AuthenticationRequest(
    val email: String,
    val password: String
)