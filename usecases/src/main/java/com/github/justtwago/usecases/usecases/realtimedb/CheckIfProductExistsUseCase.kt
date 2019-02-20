package com.github.justtwago.usecases.usecases.realtimedb

import com.github.justtwago.service.firebase.FirebaseAuthenticator
import com.github.justtwago.service.firebase.FirebaseProductCartRepository
import com.github.justtwago.usecases.model.market.common.Product
import com.github.justtwago.usecases.usecases.base.AsyncUseCase

class CheckIfProductExistsUseCase(
    private val authenticator: FirebaseAuthenticator,
    private val firebaseProductCartRepository: FirebaseProductCartRepository
) : AsyncUseCase<Product, Boolean> {
    override suspend fun execute(request: Product): Boolean {
        return firebaseProductCartRepository.checkIfProductExists(uid = authenticator.getUserId(), productId = request.id.toString())
    }
}