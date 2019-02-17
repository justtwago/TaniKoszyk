package com.github.justtwago.usecases.sample

import com.github.justtwago.usecases.model.auth.AuthenticationRequest


val authenticationRequest = AuthenticationRequest(
    email = "test@test.test",
    password = "test123"
)

const val userUuid: String = "userUuid"