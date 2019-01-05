package com.github.justtwago.tanikoszyk.model.domain

import com.github.justtwago.tanikoszyk.model.auchan.AuchanProduct
import com.github.justtwago.tanikoszyk.model.auchan.AuchanProductPage
import com.github.justtwago.tanikoszyk.model.kaufland.KauflandProduct
import com.github.justtwago.tanikoszyk.model.kaufland.KauflandProductPage
import com.github.justtwago.tanikoszyk.services.auchan.AUCHAN_BASE_URL


fun AuchanProductPage.mapToDomain(): List<Product> {
    return products.map { it.mapToDomain() }
}

fun AuchanProduct.mapToDomain(): Product {
    return Product(
        subtitle = subtitle,
        title = title.substringAfter("$subtitle - "),
        oldPrice = "",
        price = "$priceZloty,$priceCents zł",
        imageUrl = "$AUCHAN_BASE_URL${imageUrl.substring(1, imageUrl.length)}",
        quantity = quanity,
        market = Market.AUCHAN
    )
}

fun KauflandProductPage.mapToDomain(): List<Product> {
    return products.map { it.mapToDomain() }
}

fun KauflandProduct.mapToDomain(): Product {
    return Product(
        subtitle = subtitle,
        title = title,
        oldPrice = oldPrice,
        price = "$price zł",
        imageUrl = imageUrl.split(", ").last().substringBefore(" "),
        quantity = quantity,
        market = Market.KAUFLAND
    )
}