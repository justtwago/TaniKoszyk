package com.tanikoszyk.usecases.di

import com.tanikoszyk.usecases.usecases.auth.CheckIsUserSignInUseCase
import com.tanikoszyk.usecases.usecases.auth.SignInUseCase
import com.tanikoszyk.usecases.usecases.auth.SignOutUseCase
import com.tanikoszyk.usecases.usecases.auth.SignUpUseCase
import com.tanikoszyk.usecases.usecases.market.GetAuchanProductPageUseCase
import com.tanikoszyk.usecases.usecases.market.GetBiedronkaProductPageUseCase
import com.tanikoszyk.usecases.usecases.market.GetKauflandProductPageUseCase
import com.tanikoszyk.usecases.usecases.market.GetTescoProductPageUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.AddProductToCartUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.CheckIfProductExistsUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.ObserveCartProductsUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.RemoveProductFromCartUseCase
import org.koin.dsl.module.module

val usecaseModule = module {
    single { SignInUseCase(authenticator = get()) }
    single { SignUpUseCase(authenticator = get()) }
    single { SignOutUseCase(authenticator = get()) }
    single { CheckIsUserSignInUseCase(authenticator = get()) }

    single { AddProductToCartUseCase(firebaseProductCartRepository = get(), authenticator = get()) }
    single { RemoveProductFromCartUseCase(firebaseProductCartRepository = get(), authenticator = get()) }

    single {
        GetAuchanProductPageUseCase(
            auchanRepository = get(),
            checkIfProductExistsUseCase = get()
        )
    }
    single {
        GetBiedronkaProductPageUseCase(
            biedronkaRepository = get(),
            checkIfProductExistsUseCase = get()
        )
    }
    single {
        GetKauflandProductPageUseCase(
            kauflandRepository = get(),
            checkIfProductExistsUseCase = get()
        )
    }
    single {
        GetTescoProductPageUseCase(
            tescoRepository = get(),
            checkIfProductExistsUseCase = get()
        )
    }
    single { ObserveCartProductsUseCase(authenticator = get(), firebaseProductCartRepository = get()) }
    single { CheckIfProductExistsUseCase(authenticator = get(), firebaseProductCartRepository = get()) }
}