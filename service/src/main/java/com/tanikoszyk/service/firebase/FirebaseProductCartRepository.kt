package com.tanikoszyk.service.firebase

import com.fanmountain.domain.MarketProduct
import com.google.firebase.database.DatabaseReference
import com.tanikoszyk.service.common.addListenerForSingleValueEvent
import com.tanikoszyk.service.common.addValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val PRODUCTS_DB_REFERENCE = "products"

interface FirebaseProductCartRepository {
    suspend fun addProductToCart(uid: String, marketProduct: MarketProduct): Boolean
    suspend fun removeProductFromCart(uid: String, marketProduct: MarketProduct): Boolean
    suspend fun observeCartProducts(uid: String, cartProductObserver: (List<MarketProduct>) -> Unit)
    suspend fun checkIfProductExists(uid: String, productUrl: String): Boolean
}

class FirebaseProductCartRepositoryImpl(private val databaseReference: DatabaseReference) :
    FirebaseProductCartRepository {

    override suspend fun addProductToCart(uid: String, marketProduct: MarketProduct): Boolean {
        return databaseReference.child(PRODUCTS_DB_REFERENCE)
            .child(uid)
            .child(marketProduct.product.url)
            .setValue(marketProduct)
            .isSuccessful
    }

    override suspend fun removeProductFromCart(uid: String, marketProduct: MarketProduct): Boolean {
        return databaseReference.child(PRODUCTS_DB_REFERENCE)
            .child(uid)
            .child(marketProduct.product.url)
            .removeValue()
            .isSuccessful
    }

    override suspend fun observeCartProducts(uid: String, cartProductObserver: (List<MarketProduct>) -> Unit) {
        databaseReference.child(PRODUCTS_DB_REFERENCE).child(uid).addValueEventListener {
            val products = it.children.asSequence()
                .map { productSnapshot -> productSnapshot.getValue(MarketProduct::class.java)!! }
                .toList()
            cartProductObserver.invoke(products)
        }
    }

    override suspend fun checkIfProductExists(uid: String, productUrl: String): Boolean {
        return suspendCoroutine { continuation ->
            databaseReference.child(PRODUCTS_DB_REFERENCE).child(uid).addListenerForSingleValueEvent {
                continuation.resume(it.hasChild(productUrl))
            }
        }
    }
}