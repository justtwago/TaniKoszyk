package com.tanikoszyk.di

import com.tanikoszyk.ui.auth.signin.SignInViewModel
import com.tanikoszyk.ui.auth.signup.SignUpViewModel
import com.tanikoszyk.ui.cart.CartViewModel
import com.tanikoszyk.ui.home.HomeViewModel
import com.tanikoszyk.ui.home.list.paging.auchan.AuchanProductDataSourceFactory
import com.tanikoszyk.ui.home.list.paging.biedronka.BiedronkaProductDataSourceFactory
import com.tanikoszyk.ui.home.list.paging.kaufland.KauflandProductDataSourceFactory
import com.tanikoszyk.ui.home.list.paging.tesco.TescoProductDataSourceFactory
import com.tanikoszyk.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    single { AuchanProductDataSourceFactory(getAuchanProductPageUseCase = get()) }
    single { BiedronkaProductDataSourceFactory(getBiedronkaProductPageUseCase = get()) }
    single { KauflandProductDataSourceFactory(getKauflandProductPageUseCase = get()) }
    single { TescoProductDataSourceFactory(getTescoProductPageUseCase = get()) }

    viewModel {
        HomeViewModel(
            auchanProductDataSourceFactory = get(),
            biedronkaProductDataSourceFactory = get(),
            kauflandProductDataSourceFactory = get(),
            tescoProductDataSourceFactory = get(),
            addProductToCartUseCase = get()
        )
    }
    viewModel { SignInViewModel(signInUseCase = get(), checkIsUserSignInUseCase = get()) }
    viewModel { SignUpViewModel(signUpUseCase = get(), checkIsUserSignInUseCase = get()) }
    viewModel { ProfileViewModel(signOutUseCase = get()) }
    viewModel { CartViewModel(observeCartProductsUseCase = get()) }
}