package com.tanikoszyk.usecases.usecases.auth

import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.firebase.FirebaseAuthenticator
import com.tanikoszyk.usecases.model.Result
import com.tanikoszyk.usecases.sample.authenticationRequest
import com.tanikoszyk.usecases.sample.userUuid
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SignUpUseCaseTest {
    @Mock lateinit var authenticator: FirebaseAuthenticator
    @InjectMocks lateinit var useCase: SignUpUseCase

    @Test
    fun `sign in user successfully`() = runBlocking {
        whenever(authenticator.signUp(authenticationRequest.email, authenticationRequest.password))
            .thenReturn(Response.Success.WithBody(userUuid))

        val response = useCase.execute(authenticationRequest)
        assertEquals((response as Result.Success).result, userUuid)
    }

    @Test
    fun `sign in user with error`() = runBlocking {
        whenever(authenticator.signUp(authenticationRequest.email, authenticationRequest.password))
            .thenReturn(Response.Failure(Throwable()))

        val response = useCase.execute(authenticationRequest)
        assertTrue(response is Result.Failure)
    }
}