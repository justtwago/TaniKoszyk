package com.github.justtwago.tanikoszyk.model


data class Product(
        val subtitle: String,
        val title: String,
        val oldPrice: String,
        val price: String,
        val imageUrl: List<String>,
        val quantity: String,
        val market: Market
)