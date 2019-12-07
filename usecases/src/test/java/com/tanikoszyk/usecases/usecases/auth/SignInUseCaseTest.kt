package com.tanikoszyk.usecases.usecases.auth

import com.nhaarman.mockitokotlin2.whenever
import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.firebase.FirebaseAuthenticator
import com.tanikoszyk.usecases.requests.Result
import com.tanikoszyk.usecases.sample.authenticationRequest
import com.tanikoszyk.usecases.sample.userUuid
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SignInUseCaseTest {

    @Mock lateinit var authenticator: FirebaseAuthenticator
    @InjectMocks lateinit var useCase: SignInUseCase

    @Test
    fun `sign in user successfully`() = runBlocking {
        whenever(authenticator.signIn(authenticationRequest.email, authenticationRequest.password))
            .thenReturn(Response.Success.WithBody(userUuid))

        val response = useCase.execute(authenticationRequest)
        assertEquals((response as Result.Success).result, userUuid)
    }

    @Test
    fun `sign in user with error`() = runBlocking {
        whenever(authenticator.signIn(authenticationRequest.email, authenticationRequest.password))
            .thenReturn(Response.Failure(Throwable()))

        val response = useCase.execute(authenticationRequest)
        assertTrue(response is Result.Failure)
    }
}