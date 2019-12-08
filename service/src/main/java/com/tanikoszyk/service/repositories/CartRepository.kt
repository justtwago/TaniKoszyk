package com.tanikoszyk.service.repositories

import com.fanmountain.authentication.Authenticator
import com.google.firebase.database.DatabaseReference
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.service.common.addListenerForSingleValueEvent
import com.tanikoszyk.service.common.addValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val PRODUCTS_DB_REFERENCE = "products"

interface CartRepository {
    suspend fun addProductToCart(marketProduct: MarketProduct): Boolean
    suspend fun removeProductFromCart(marketProduct: MarketProduct): Boolean
    suspend fun observeCartProducts(cartProductObserver: (List<MarketProduct>) -> Unit)
    suspend fun checkIfProductExists(productUrl: String): Boolean
}

internal class FirebaseProductCartRepository(
    private val databaseReference: DatabaseReference,
    private val authenticator: Authenticator
) : CartRepository {

    override suspend fun addProductToCart(marketProduct: MarketProduct): Boolean {
        return databaseReference.child(PRODUCTS_DB_REFERENCE)
            .child(authenticator.getUserId())
            .child(marketProduct.product.url)
            .setValue(marketProduct)
            .isSuccessful
    }

    override suspend fun removeProductFromCart(marketProduct: MarketProduct): Boolean {
        return databaseReference.child(PRODUCTS_DB_REFERENCE)
            .child(authenticator.getUserId())
            .child(marketProduct.product.url)
            .removeValue()
            .isSuccessful
    }

    override suspend fun observeCartProducts(cartProductObserver: (List<MarketProduct>) -> Unit) {
        databaseReference.child(PRODUCTS_DB_REFERENCE)
            .child(authenticator.getUserId())
            .addValueEventListener {
                val products = it.children.asSequence()
                    .map { productSnapshot -> productSnapshot.getValue(MarketProduct::class.java)!! }
                    .toList()
                cartProductObserver.invoke(products)
            }
    }

    override suspend fun checkIfProductExists(productUrl: String): Boolean {
        val userId = authenticator.getUserId()
        return suspendCoroutine { continuation ->
            databaseReference.child(PRODUCTS_DB_REFERENCE)
                .child(userId)
                .addListenerForSingleValueEvent {
                    continuation.resume(it.hasChild(productUrl))
                }
        }
    }
}