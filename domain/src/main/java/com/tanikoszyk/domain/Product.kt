package com.tanikoszyk.domain

data class Product(
    val url: String,
    val subtitle: String,
    val title: String,
    val price: Money,
    val imageUrl: String,
    val quantity: String
)