package com.github.justtwago.usecases.usecases.realtimedb

import com.github.justtwago.service.firebase.FirebaseProductCartRepository
import com.github.justtwago.usecases.model.market.common.Product
import com.github.justtwago.usecases.model.market.common.mapToService
import com.github.justtwago.usecases.usecases.base.AsyncUseCase


class AddProductToCartUseCase(private val firebaseProductCartRepository: FirebaseProductCartRepository) : AsyncUseCase<Product, Unit> {
    override suspend fun execute(request: Product) {
        firebaseProductCartRepository.addProductToCart(request.mapToService())
    }
}