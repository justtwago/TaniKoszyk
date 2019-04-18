package com.tanikoszyk.usecases.usecases.realtimedb

import com.tanikoszyk.service.firebase.FirebaseAuthenticator
import com.tanikoszyk.service.firebase.FirebaseProductCartRepository
import com.tanikoszyk.usecases.model.market.common.Product
import com.tanikoszyk.usecases.model.market.common.mapToService
import com.tanikoszyk.usecases.usecases.base.AsyncUseCase

class AddProductToCartUseCase(
    private val firebaseProductCartRepository: FirebaseProductCartRepository,
    private val authenticator: FirebaseAuthenticator
) : AsyncUseCase<Product, Boolean> {
    override suspend fun execute(request: Product): Boolean {
        return firebaseProductCartRepository.addProductToCart(authenticator.getUserId(), request.mapToService())
    }
}