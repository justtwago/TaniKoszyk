package com.github.justtwago.service.di

import com.github.justtwago.service.repositories.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module


val serviceModule = module {
    single { AuchanRepositoryImpl(androidApplication()) as AuchanRepository }
    single { KauflandRepositoryImpl(androidApplication()) as KauflandRepository }
    single { TescoRepositoryImpl(androidApplication()) as TescoRepository }
    single { BiedronkaRepositoryImpl(androidApplication()) as BiedronkaRepository }
    single {
        ProductRepositoryImpl(
            auchanRepository = get(),
            kauflandRepository = get(),
            tescoRepository = get(),
            biedronkaRepository = get()
        ) as ProductRepository
    }
}