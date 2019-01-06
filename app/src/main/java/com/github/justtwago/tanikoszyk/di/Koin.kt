package com.github.justtwago.tanikoszyk.di

import com.github.justtwago.tanikoszyk.services.auchan.AuchanRepository
import com.github.justtwago.tanikoszyk.services.auchan.AuchanRepositoryImpl
import com.github.justtwago.tanikoszyk.services.base.MarketsRepository
import com.github.justtwago.tanikoszyk.services.base.MarketsRepositoryImpl
import com.github.justtwago.tanikoszyk.services.kaufland.KauflandRepository
import com.github.justtwago.tanikoszyk.services.kaufland.KauflandRepositoryImpl
import com.github.justtwago.tanikoszyk.services.tesco.TescoRepository
import com.github.justtwago.tanikoszyk.services.tesco.TescoRepositoryImpl
import com.github.justtwago.tanikoszyk.ui.search.SearchViewModel
import com.github.justtwago.tanikoszyk.ui.search.list.ProductDataSourceFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {
    single { AuchanRepositoryImpl(androidApplication()) as AuchanRepository }
    single { KauflandRepositoryImpl(androidApplication()) as KauflandRepository }
    single { TescoRepositoryImpl(androidApplication()) as TescoRepository }
    single {
        MarketsRepositoryImpl(
            auchanRepository = get(),
            kauflandRepository = get(),
            tescoRepository = get()
        ) as MarketsRepository
    }

    single { ProductDataSourceFactory(repository = get()) }

    viewModel { SearchViewModel(productDataSourceFactory = get()) }
}