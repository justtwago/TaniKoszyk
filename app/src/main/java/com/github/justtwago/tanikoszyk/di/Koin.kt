package com.github.justtwago.tanikoszyk.di

import com.github.justtwago.tanikoszyk.services.auchan.AuchanRepository
import com.github.justtwago.tanikoszyk.services.auchan.AuchanRepositoryImpl
import com.github.justtwago.tanikoszyk.services.base.BaseRepository
import com.github.justtwago.tanikoszyk.services.base.BaseRepositoryImpl
import com.github.justtwago.tanikoszyk.services.kaufland.KauflandRepository
import com.github.justtwago.tanikoszyk.services.kaufland.KauflandRepositoryImpl
import com.github.justtwago.tanikoszyk.services.tesco.TescoRepository
import com.github.justtwago.tanikoszyk.services.tesco.TescoRepositoryImpl
import com.github.justtwago.tanikoszyk.ui.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {
    single { AuchanRepositoryImpl(androidApplication()) as AuchanRepository }
    single { KauflandRepositoryImpl(androidApplication()) as KauflandRepository }
    single { TescoRepositoryImpl(androidApplication()) as TescoRepository }
    single {
        BaseRepositoryImpl(
            auchanRepository = get(),
            kauflandRepository = get(),
            tescoRepository = get()
        ) as BaseRepository
    }

    viewModel { MainViewModel(baseRepository = get()) }
}