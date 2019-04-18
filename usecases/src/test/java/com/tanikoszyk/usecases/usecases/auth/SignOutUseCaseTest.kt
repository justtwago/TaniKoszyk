package com.tanikoszyk.usecases.usecases.auth

import com.tanikoszyk.service.firebase.FirebaseAuthenticator
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SignOutUseCaseTest {
    @Mock lateinit var authenticator: FirebaseAuthenticator
    @InjectMocks lateinit var useCase: SignOutUseCase

    @Test
    fun `sign out user successfully`() = runBlocking {
        useCase.execute()
        verify(authenticator).signOut()
    }

}