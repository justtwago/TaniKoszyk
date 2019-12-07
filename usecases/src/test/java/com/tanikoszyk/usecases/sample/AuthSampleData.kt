package com.tanikoszyk.usecases.sample

import com.tanikoszyk.usecases.requests.AuthenticationRequest


val authenticationRequest = AuthenticationRequest(
    email = "test@test.test",
    password = "test123"
)

const val userUuid: String = "userUuid"