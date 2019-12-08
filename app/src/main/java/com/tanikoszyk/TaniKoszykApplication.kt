package com.tanikoszyk

import android.app.Application
import com.tanikoszyk.di.application.AppComponentProvider
import com.tanikoszyk.di.application.DaggerAppComponent

class TaniKoszykApplication : Application(), AppComponentProvider {

    override val appComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent.inject(this)
    }
}