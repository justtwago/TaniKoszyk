package com.tanikoszyk.usecases.usecases.realtimedb

import com.fanmountain.domain.MarketProduct
import com.tanikoszyk.service.firebase.FirebaseAuthenticator
import com.tanikoszyk.service.firebase.FirebaseProductCartRepository
import com.tanikoszyk.usecases.usecases.base.AsyncUseCase

class CheckIfProductExistsUseCase(
    private val authenticator: FirebaseAuthenticator,
    private val firebaseProductCartRepository: FirebaseProductCartRepository
) : AsyncUseCase<MarketProduct, Boolean> {

    override suspend fun execute(request: MarketProduct):Boolean {
        return firebaseProductCartRepository.checkIfProductExists(
            uid = authenticator.getUserId(),
            productUrl = request.product.url
        )
    }
}