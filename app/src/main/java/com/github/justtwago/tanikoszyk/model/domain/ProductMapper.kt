package com.github.justtwago.tanikoszyk.model.domain

import com.github.justtwago.tanikoszyk.model.auchan.AuchanProduct
import com.github.justtwago.tanikoszyk.model.auchan.AuchanProductPage
import com.github.justtwago.tanikoszyk.model.kaufland.KauflandProduct
import com.github.justtwago.tanikoszyk.model.kaufland.KauflandProductPage
import com.github.justtwago.tanikoszyk.model.tesco.TescoProduct
import com.github.justtwago.tanikoszyk.model.tesco.TescoProductPage
import com.github.justtwago.tanikoszyk.services.auchan.AUCHAN_BASE_URL
import com.github.justtwago.tanikoszyk.services.auchan.AUCHAN_PAGE_SIZE
import com.github.justtwago.tanikoszyk.services.kaufland.KAUFLAND_PAGE_SIZE
import com.github.justtwago.tanikoszyk.services.tesco.TESCO_PAGE_SIZE
import com.github.justtwago.tanikoszyk.ui.search.list.SearchProductItemViewModel


fun AuchanProductPage.mapToDomain(): ProductPage {
    return ProductPage(
        products = products?.map { it.mapToDomain() }?.filter { it.isNotEmpty() }.orEmpty(),
        pageCount = size?.toInt() ?: 0
    )
}

fun AuchanProduct.mapToDomain(): Product {
    return Product(
        id = toString().hashCode(), //TODO: get ids from tesco
        subtitle = subtitle.orEmpty(),
        title = title?.substringAfter("$subtitle - ").orEmpty(),
        oldPrice = "",
        price = "${priceZloty ?: "0"},${priceCents ?: "0"} zł",
        imageUrl = "$AUCHAN_BASE_URL${imageUrl.orEmpty()}",
        quantity = quanity.orEmpty(),
        market = Market.AUCHAN
    )
}

fun KauflandProductPage.mapToDomain(): ProductPage {
    return ProductPage(
        products = products?.map { it.mapToDomain() }?.filter { it.isNotEmpty() }.orEmpty(),
        pageCount = size?.mapToKauflandPageCountDomain() ?: 0
    )
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

fun String.mapToKauflandPageCountDomain(): Int {
    val productCount = substringAfter("(").substringBefore(")").toInt()
    var pageCount = productCount / KAUFLAND_PAGE_SIZE
    if (productCount % KAUFLAND_PAGE_SIZE != 0) pageCount++
    return pageCount
}

fun TescoProductPage.mapToDomain(): ProductPage {
    return ProductPage(
        products = products?.map { it.mapToDomain() }?.filter { it.isNotEmpty() }.orEmpty(),
        pageCount = size?.mapToTescoPageCountDomain() ?: 0
    )
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

fun String.mapToTescoPageCountDomain(): Int {
    val productCount = substringBefore(" szt").toInt()
    var pageCount = productCount / TESCO_PAGE_SIZE
    if (productCount % TESCO_PAGE_SIZE != 0) pageCount++
    return pageCount
}

private fun TescoProduct.mapQuantity(): String {
    return if (quantity.orEmpty().isEmpty()) {
        "${weight ?: 0}"
    } else "${quantity ?: 0} szt"
}

fun Product.toViewModel() = SearchProductItemViewModel(this)