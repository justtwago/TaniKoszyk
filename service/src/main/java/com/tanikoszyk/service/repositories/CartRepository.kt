package com.tanikoszyk.service.repositories

import com.tanikoszyk.domain.MarketProduct

interface CartRepository {
    suspend fun addProductToCart(marketProduct: MarketProduct): Boolean
    suspend fun removeProductFromCart(marketProduct: MarketProduct): Boolean
    suspend fun observeCartProducts(cartProductObserver: (List<MarketProduct>) -> Unit)
    suspend fun checkIfProductExists(productUrl: String): Boolean
}