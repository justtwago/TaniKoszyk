package com.github.justtwago.tanikoszyk.di

import com.github.justtwago.tanikoszyk.ui.auth.AuthenticationViewModel
import com.github.justtwago.tanikoszyk.ui.home.HomeViewModel
import com.github.justtwago.tanikoszyk.ui.home.list.paging.auchan.AuchanProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.home.list.paging.biedronka.BiedronkaProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.home.list.paging.kaufland.KauflandProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.home.list.paging.tesco.TescoProductDataSourceFactory
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {

    single { AuchanProductDataSourceFactory(repository = get()) }
    single { BiedronkaProductDataSourceFactory(repository = get()) }
    single { KauflandProductDataSourceFactory(repository = get()) }
    single { TescoProductDataSourceFactory(repository = get()) }

    viewModel { HomeViewModel(
        auchanProductDataSourceFactory = get(),
        biedronkaProductDataSourceFactory = get(),
        kauflandProductDataSourceFactory = get(),
        tescoProductDataSourceFactory = get()
    ) }
    viewModel { AuthenticationViewModel(authenticator = get()) }
}