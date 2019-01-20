package com.github.justtwago.tanikoszyk.di

import com.github.justtwago.tanikoszyk.ui.search.SearchViewModel
import com.github.justtwago.tanikoszyk.ui.search.list.ProductDataSourceFactory
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {

    single { ProductDataSourceFactory(repository = get()) }

    viewModel { SearchViewModel(productDataSourceFactory = get()) }
}