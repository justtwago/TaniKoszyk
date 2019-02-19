package com.github.justtwago.usecases.model.market.common


data class Product(
        val id: Int,
        val subtitle: String,
        val title: String,
        val price: String,
        val imageUrl: String,
        val quantity: String,
        val market: Market
) {
    override fun toString(): String {
        return "Subtitle: $subtitle\n" +
                "Title: $title\n" +
                "Url: $imageUrl\n" +
                "Price: $price\n" +
                "Quantity: $quantity\n" +
                "MarketService: ${market.name}"
    }

    fun isNotEmpty(): Boolean {
        return subtitle.isNotEmpty()
                && imageUrl.isNotEmpty()
                && price != "0 zł"
                && price != "0,0 zł"
    }
}

data class ProductPage(
        val products: List<Product>,
        val pageCount: Int
)

data class ProductIdPage(
        val productIdList: List<String>,
        val pageCount: Int
)