package com.tanikoszyk

import android.app.Application
import com.tanikoszyk.di.application.AppComponentProvider
import com.tanikoszyk.di.application.DaggerAppComponent
import com.tanikoszyk.service.di.DaggerServiceComponent

class TaniKoszykApplication : Application(), AppComponentProvider {

    private val serviceComponent by lazy {
        DaggerServiceComponent
            .factory()
            .create(applicationContext)
    }
    override val appComponent by lazy {
        DaggerAppComponent
            .factory()
            .create(serviceComponent, applicationContext = this)
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