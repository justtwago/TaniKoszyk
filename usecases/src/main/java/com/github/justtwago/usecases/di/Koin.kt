package com.github.justtwago.usecases.di

import com.github.justtwago.usecases.usecases.auth.CheckIsUserSignInUseCase
import com.github.justtwago.usecases.usecases.auth.SignInUseCase
import com.github.justtwago.usecases.usecases.auth.SignOutUseCase
import com.github.justtwago.usecases.usecases.auth.SignUpUseCase
import com.github.justtwago.usecases.usecases.market.GetAuchanProductPageUseCase
import com.github.justtwago.usecases.usecases.market.GetBiedronkaProductPageUseCase
import com.github.justtwago.usecases.usecases.market.GetKauflandProductPageUseCase
import com.github.justtwago.usecases.usecases.market.GetTescoProductPageUseCase
import com.github.justtwago.usecases.usecases.realtimedb.AddProductToCartUseCase
import com.github.justtwago.usecases.usecases.realtimedb.CheckIfProductExistsUseCase
import com.github.justtwago.usecases.usecases.realtimedb.ObserveCartProductsUseCase
import org.koin.dsl.module.module

val usecaseModule = module {
    single { SignInUseCase(authenticator = get()) }
    single { SignUpUseCase(authenticator = get()) }
    single { SignOutUseCase(authenticator = get()) }
    single { CheckIsUserSignInUseCase(authenticator = get()) }

    single { AddProductToCartUseCase(firebaseProductCartRepository = get(), authenticator = get()) }

    single { GetAuchanProductPageUseCase(auchanRepository = get()) }
    single { GetBiedronkaProductPageUseCase(biedronkaRepository = get()) }
    single { GetKauflandProductPageUseCase(kauflandRepository = get()) }
    single { GetTescoProductPageUseCase(tescoRepository = get()) }
    single { ObserveCartProductsUseCase(authenticator = get(), firebaseProductCartRepository = get()) }
    single { CheckIfProductExistsUseCase(authenticator = get(), firebaseProductCartRepository = get()) }
}