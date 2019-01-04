package com.github.justtwago.tanikoszyk.model.domain


data class Product(
        val subtitle: String,
        val title: String,
        val oldPrice: String,
        val price: String,
        val imageUrl: String,
        val quantity: String,
        val market: Market
)