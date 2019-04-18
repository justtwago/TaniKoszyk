package com.tanikoszyk.usecases.usecases.auth

import com.tanikoszyk.service.firebase.FirebaseAuthenticator
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.*
import kotlinx.coroutines.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CheckIsUserSignInUseCaseTest {
    @Mock lateinit var authenticator: FirebaseAuthenticator
    @InjectMocks lateinit var useCase: CheckIsUserSignInUseCase

    @Test
    fun `return true when check if user is sign in`() = runBlocking {
        whenever(authenticator.isUserLoggedIn()).thenReturn(true)

        val result = useCase.execute()
        assertTrue(result)
    }

    @Test
    fun `return false when check if user is not sign in`() = runBlocking {
        whenever(authenticator.isUserLoggedIn()).thenReturn(false)

        val result = useCase.execute()
        assertFalse(result)
    }
}