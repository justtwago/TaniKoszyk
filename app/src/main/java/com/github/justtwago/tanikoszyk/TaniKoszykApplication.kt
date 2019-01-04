package com.github.justtwago.tanikoszyk

import android.app.Application
import com.github.justtwago.tanikoszyk.di.appModule
import org.koin.android.ext.android.startKoin


class TaniKoszykApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}