package com.fanmountain.authentication

import dagger.Component

@Component(modules = [AuthenticationModule::class])
interface AuthenticatorComponent {

    val authenticator: Authenticator

    @Component.Factory
    interface Factory {

        fun create(): AuthenticatorComponent
    }
}