package com.github.justtwago.tanikoszyk.model.domain


data class Product(
        val id: String,
        val subtitle: String,
        val title: String,
        val oldPrice: String,
        val price: String,
        val imageUrl: String,
        val quantity: String,
        val market: Market
) {
    override fun toString(): String {
        return "Subtitle: $subtitle\n" +
                "Title: $title\n" +
                "Url: $imageUrl\n" +
                "Old price: $oldPrice\n" +
                "Price: $price\n" +
                "Quantity: $quantity\n" +
                "Market: ${market.name}"
    }
}