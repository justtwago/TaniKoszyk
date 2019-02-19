package com.github.justtwago.service.firebase

import com.github.justtwago.service.common.addValueEventListener
import com.github.justtwago.service.model.service.ProductService
import com.google.firebase.database.DatabaseReference

interface FirebaseProductCartRepository {
    suspend fun addProductToCart(uid: String, product: ProductService)
    suspend fun observeCartProducts(uid: String, cartProductObserver: (List<ProductService>) -> Unit)
}

class FirebaseProductCartRepositoryImpl(private val databaseReference: DatabaseReference) : FirebaseProductCartRepository {
    override suspend fun addProductToCart(uid: String, product: ProductService) {
        databaseReference.child("products").child(uid).child(product.id.toString()).setValue(product)
    }

    override suspend fun observeCartProducts(uid: String, cartProductObserver: (List<ProductService>) -> Unit) {
        databaseReference.child("products").child(uid).addValueEventListener {
            val products = it.children.asSequence()
                .map { productSnapshot -> productSnapshot.getValue(ProductService::class.java)!! }
                .toList()
            cartProductObserver.invoke(products)
        }
    }
}