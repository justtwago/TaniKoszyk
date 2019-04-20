package com.tanikoszyk.service.firebase

import com.tanikoszyk.service.common.addListenerForSingleValueEvent
import com.tanikoszyk.service.common.addValueEventListener
import com.tanikoszyk.service.model.service.ProductService
import com.google.firebase.database.DatabaseReference
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val PRODUCTS_DB_REFERENCE = "products"

interface FirebaseProductCartRepository {
    suspend fun addProductToCart(uid: String, product: ProductService): Boolean
    suspend fun removeProductFromCart(uid: String, product: ProductService): Boolean
    suspend fun observeCartProducts(uid: String, cartProductObserver: (List<ProductService>) -> Unit)
    suspend fun checkIfProductExists(uid: String, productId: String): Boolean
}

class FirebaseProductCartRepositoryImpl(private val databaseReference: DatabaseReference) :
    FirebaseProductCartRepository {

    override suspend fun addProductToCart(uid: String, product: ProductService): Boolean {
        return databaseReference.child(PRODUCTS_DB_REFERENCE)
            .child(uid)
            .child(product.id)
            .setValue(product)
            .isSuccessful
    }

    override suspend fun removeProductFromCart(uid: String, product: ProductService): Boolean {
        return databaseReference.child(PRODUCTS_DB_REFERENCE)
            .child(uid)
            .child(product.id)
            .removeValue()
            .isSuccessful
    }

    override suspend fun observeCartProducts(uid: String, cartProductObserver: (List<ProductService>) -> Unit) {
        databaseReference.child(PRODUCTS_DB_REFERENCE).child(uid).addValueEventListener {
            val products = it.children.asSequence()
                .map { productSnapshot -> productSnapshot.getValue(ProductService::class.java)!! }
                .toList()
            cartProductObserver.invoke(products)
        }
    }

    override suspend fun checkIfProductExists(uid: String, productId: String): Boolean {
        return suspendCoroutine { continuation ->
            databaseReference.child(PRODUCTS_DB_REFERENCE).child(uid).addListenerForSingleValueEvent {
                continuation.resume(it.hasChild(productId))
            }
        }
    }
}