package com.tanikoszyk.usecases.usecases.realtimedb

import com.fanmountain.domain.MarketProduct
import com.tanikoszyk.service.firebase.FirebaseAuthenticator
import com.tanikoszyk.service.firebase.FirebaseProductCartRepository
import com.tanikoszyk.usecases.usecases.base.AsyncUseCase

private typealias CartProductObserver = (List<MarketProduct>) -> Unit

class ObserveCartProductsUseCase(
    private val authenticator: FirebaseAuthenticator,
    private val firebaseProductCartRepository: FirebaseProductCartRepository
) : AsyncUseCase<CartProductObserver, Unit> {

    override suspend fun execute(request: CartProductObserver) {
        firebaseProductCartRepository.observeCartProducts(authenticator.getUserId()) {
            request.invoke(it)
        }
    }
}