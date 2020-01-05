package com.tanikoszyk.service.di

import android.content.Context
import com.tanikoszyk.service.common.createRetrofit
import com.tanikoszyk.service.repositories.AUCHAN_BASE_URL
import com.tanikoszyk.service.repositories.BIEDRONKA_BASE_URL
import com.tanikoszyk.service.repositories.KAUFLAND_BASE_URL
import dagger.Module
import dagger.Provides

@Module
internal class ServiceModule {

    @Provides
    @Auchan
    fun auchanRetrofit(context: Context) = createRetrofit(context, AUCHAN_BASE_URL)

    @Provides
    @Biedronka
    fun biedronkaRetrofit(context: Context) = createRetrofit(context, BIEDRONKA_BASE_URL)

    @Provides
    @Kaufland
    fun kauflandRetrofit(context: Context) = createRetrofit(context, KAUFLAND_BASE_URL)
}