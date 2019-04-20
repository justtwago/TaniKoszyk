package com.tanikoszyk

import android.app.Application
import com.tanikoszyk.service.di.serviceModule
import com.tanikoszyk.di.appModule
import com.tanikoszyk.usecases.di.usecaseModule
import org.koin.android.ext.android.startKoin


class TaniKoszykApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, usecaseModule, serviceModule))
    }
}