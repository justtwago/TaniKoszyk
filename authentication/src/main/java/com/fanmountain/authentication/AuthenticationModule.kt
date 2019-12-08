package com.fanmountain.authentication

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthenticationModule {

    @Provides
    @Singleton
    fun firebaseAuthenticator(auth: FirebaseAuth): Authenticator = FirebaseAuthenticator(auth)
}