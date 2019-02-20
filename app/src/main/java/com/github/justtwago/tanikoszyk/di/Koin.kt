package com.github.justtwago.tanikoszyk.di

import com.github.justtwago.tanikoszyk.ui.auth.signin.SignInViewModel
import com.github.justtwago.tanikoszyk.ui.auth.signup.SignUpViewModel
import com.github.justtwago.tanikoszyk.ui.cart.CartViewModel
import com.github.justtwago.tanikoszyk.ui.home.HomeViewModel
import com.github.justtwago.tanikoszyk.ui.home.list.paging.auchan.AuchanProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.home.list.paging.biedronka.BiedronkaProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.home.list.paging.kaufland.KauflandProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.home.list.paging.tesco.TescoProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    single { AuchanProductDataSourceFactory(getAuchanProductPageUseCase = get(), checkIfProductExistsUseCase = get()) }
    single { BiedronkaProductDataSourceFactory(getBiedronkaProductPageUseCase = get(), checkIfProductExistsUseCase = get()) }
    single { KauflandProductDataSourceFactory(getKauflandProductPageUseCase = get(), checkIfProductExistsUseCase = get()) }
    single { TescoProductDataSourceFactory(getTescoProductPageUseCase = get(), checkIfProductExistsUseCase = get()) }

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