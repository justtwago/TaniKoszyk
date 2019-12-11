package com.tanikoszyk

import android.app.Application
import com.fanmountain.authentication.DaggerAuthenticatorComponent
import com.tanikoszyk.di.application.AppComponentProvider
import com.tanikoszyk.di.application.DaggerAppComponent
import com.tanikoszyk.service.di.DaggerServiceComponent

class TaniKoszykApplication : Application(), AppComponentProvider {

    private val authenticatorComponent by lazy {
        DaggerAuthenticatorComponent
            .factory()
            .create()
    }

    private val serviceComponent by lazy {
        DaggerServiceComponent
            .factory()
            .create(
                authenticatorComponent,
                applicationContext
            )
    }
    override val appComponent by lazy {
        DaggerAppComponent
            .factory()
            .create(
                authenticatorComponent,
                serviceComponent,
                this
            )
    }

    override fun onCreate() {
        super.onCreate()
        initServiceComponent()
        initAppComponent()
    }

    private fun initServiceComponent() {
        serviceComponent.inject(this)
    }

    private fun initAppComponent() {
        appComponent.inject(this)
    }
}