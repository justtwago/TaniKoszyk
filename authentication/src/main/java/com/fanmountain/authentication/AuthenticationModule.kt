package com.fanmountain.authentication

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module
internal class AuthenticationModule {

    @Provides
    fun firebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun firebaseAuthenticator(auth: FirebaseAuth): Authenticator = FirebaseAuthenticator(auth)
}