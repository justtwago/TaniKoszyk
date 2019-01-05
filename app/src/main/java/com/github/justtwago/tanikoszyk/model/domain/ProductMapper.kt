package com.github.justtwago.tanikoszyk.model.domain

import com.github.justtwago.tanikoszyk.model.auchan.AuchanProduct
import com.github.justtwago.tanikoszyk.model.auchan.AuchanProductPage
import com.github.justtwago.tanikoszyk.model.kaufland.KauflandProduct
import com.github.justtwago.tanikoszyk.model.kaufland.KauflandProductPage
import com.github.justtwago.tanikoszyk.model.tesco.TescoProduct
import com.github.justtwago.tanikoszyk.model.tesco.TescoProductPage
import com.github.justtwago.tanikoszyk.services.auchan.AUCHAN_BASE_URL
import com.github.justtwago.tanikoszyk.ui.search.list.SearchProductItemViewModel


fun AuchanProductPage.mapToDomain(): List<Product> {
    return products?.map { it.mapToDomain() }?.filter { it.isNotEmpty() }.orEmpty()
}

fun AuchanProduct.mapToDomain(): Product {
    return Product(
        id = toString().hashCode(), //TODO: get ids from tesco
        subtitle = subtitle.orEmpty(),
        title = title?.substringAfter("$subtitle - ").orEmpty(),
        oldPrice = "",
        price = "${priceZloty ?: "0"},${priceCents ?: "0"} zł",
        imageUrl = "$AUCHAN_BASE_URL${imageUrl?.substring(1, imageUrl?.length ?: 0).orEmpty()}",
        quantity = quanity.orEmpty(),
        market = Market.AUCHAN
    )
}

fun KauflandProductPage.mapToDomain(): List<Product> {
    return products?.map { it.mapToDomain() }?.filter { it.isNotEmpty() }.orEmpty()
}

fun KauflandProduct.mapToDomain(): Product {
    return Product(
        id = toString().hashCode(), //TODO: get ids from tesco
        subtitle = subtitle.orEmpty(),
        title = title.orEmpty(),
        oldPrice = oldPrice.orEmpty(),
        price = "${price ?: 0} zł",
        imageUrl = imageUrl?.split(", ")?.last()?.substringBefore(" ").orEmpty(),
        quantity = quantity.orEmpty(),
        market = Market.KAUFLAND
    )
}

fun TescoProductPage.mapToDomain(): List<Product> {
    return products?.map { it.mapToDomain() }?.filter { it.isNotEmpty() }.orEmpty()
}

fun TescoProduct.mapToDomain(): Product {
    val title = title?.split(" ")
    return Product(
        id = toString().hashCode(), //TODO: get ids from tesco
        subtitle = title?.subList(0, title.size / 2)?.joinToString(separator = " ").orEmpty(),
        title = title?.subList(title.size / 2, title.size)?.joinToString(separator = " ").orEmpty(),
        oldPrice = "",
        price = "${price ?: 0} zł",
        imageUrl = imageUrl.orEmpty(),
        quantity = mapQuantity(),
        market = Market.TESCO
    )
}

private fun TescoProduct.mapQuantity(): String {
    return if (quantity.orEmpty().isEmpty()) {
        "${weight ?: 0}"
    } else "${quantity ?: 0} szt"
}

fun Product.toViewModel() = SearchProductItemViewModel(this)