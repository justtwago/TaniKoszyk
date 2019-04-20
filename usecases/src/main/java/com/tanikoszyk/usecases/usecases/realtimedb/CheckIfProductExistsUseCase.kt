package com.tanikoszyk.usecases.usecases.realtimedb

import com.tanikoszyk.service.firebase.FirebaseAuthenticator
import com.tanikoszyk.service.firebase.FirebaseProductCartRepository
import com.tanikoszyk.usecases.model.market.common.Product
import com.tanikoszyk.usecases.usecases.base.AsyncUseCase

class CheckIfProductExistsUseCase(
    private val authenticator: FirebaseAuthenticator,
    private val firebaseProductCartRepository: FirebaseProductCartRepository
) : AsyncUseCase<Product, Boolean> {
    override suspend fun execute(request: Product): Boolean {
        return firebaseProductCartRepository.checkIfProductExists(uid = authenticator.getUserId(), productUrl = request.url)
    }
}