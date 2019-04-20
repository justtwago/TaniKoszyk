package com.tanikoszyk.usecases.model.market.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val url: String,
    val subtitle: String,
    val title: String,
    val price: String,
    val imageUrl: String,
    val quantity: String,
    val market: Market
) : Parcelable {

    override fun toString(): String {
        return "Url: $url\n" +
                "Subtitle: $subtitle\n" +
                "Title: $title\n" +
                "Url: $imageUrl\n" +
                "Price: $price\n" +
                "Quantity: $quantity\n" +
                "MarketService: ${market.name}"
    }

    fun isNotEmpty(): Boolean {
        return url.isNotEmpty()
                && subtitle.isNotEmpty()
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