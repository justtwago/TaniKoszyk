package com.github.justtwago.usecases.usecases.realtimedb

import com.github.justtwago.service.firebase.FirebaseAuthenticator
import com.github.justtwago.service.firebase.FirebaseProductCartRepository
import com.github.justtwago.usecases.model.market.common.Product
import com.github.justtwago.usecases.model.market.common.mapToDomain
import com.github.justtwago.usecases.usecases.base.AsyncUseCase

private typealias CartProductObserver = (List<Product>) -> Unit

class ObserveCartProductsUseCase(
    private val firebaseProductCartRepository: FirebaseProductCartRepository,
    private val authenticator: FirebaseAuthenticator
) : AsyncUseCase<CartProductObserver, Unit> {
    override suspend fun execute(request: CartProductObserver) {
        firebaseProductCartRepository.observeCartProducts(authenticator.getUserId()) {
            request.invoke(it.map { productService -> productService.mapToDomain() })
        }
    }
}