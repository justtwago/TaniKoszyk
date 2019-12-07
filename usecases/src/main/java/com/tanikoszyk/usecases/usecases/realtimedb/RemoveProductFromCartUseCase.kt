package com.tanikoszyk.usecases.usecases.realtimedb

import com.fanmountain.domain.MarketProduct
import com.tanikoszyk.service.firebase.FirebaseAuthenticator
import com.tanikoszyk.service.firebase.FirebaseProductCartRepository
import com.tanikoszyk.usecases.usecases.base.AsyncUseCase

class RemoveProductFromCartUseCase(
    private val firebaseProductCartRepository: FirebaseProductCartRepository,
    private val authenticator: FirebaseAuthenticator
) : AsyncUseCase<MarketProduct, Boolean> {
    override suspend fun execute(request: MarketProduct): Boolean {
        return firebaseProductCartRepository.removeProductFromCart(authenticator.getUserId(), request)
    }
}