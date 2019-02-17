package com.github.justtwago.service.firebase

import com.github.justtwago.service.model.service.ProductService
import com.google.firebase.database.DatabaseReference

interface FirebaseProductCartRepository {
    suspend fun addProductToCart(product: ProductService)
}

class FirebaseProductCartRepositoryImpl(private val databaseReference: DatabaseReference) : FirebaseProductCartRepository {
    override suspend fun addProductToCart(product: ProductService) {
        databaseReference.child("products").push().key?.let {
            databaseReference.child("products").child(it).setValue(product)
        }
    }
}