package com.github.justtwago.service.model.service


data class ProductService(
        val id: Int,
        val subtitle: String,
        val title: String,
        val price: String,
        val imageUrl: String,
        val quantity: String,
        val market: String,
        var uuid: String = ""
)