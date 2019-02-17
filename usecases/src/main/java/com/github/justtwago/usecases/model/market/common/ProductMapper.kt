package com.github.justtwago.usecases.model.market.common

import com.github.justtwago.service.model.auchan.AuchanProduct
import com.github.justtwago.service.model.auchan.AuchanProductPage
import com.github.justtwago.service.model.biedronka.BiedronkaProduct
import com.github.justtwago.service.model.biedronka.BiedronkaProductIdPage
import com.github.justtwago.service.model.kaufland.KauflandProduct
import com.github.justtwago.service.model.kaufland.KauflandProductPage
import com.github.justtwago.service.model.service.ProductService
import com.github.justtwago.service.model.tesco.TescoProduct
import com.github.justtwago.service.model.tesco.TescoProductPage
import com.github.justtwago.service.repositories.AUCHAN_BASE_URL


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
        imageUrl = "$AUCHAN_BASE_URL${imageUrl?.substring(1, imageUrl?.length ?: 0).orEmpty()}",
        quantity = quantity.orEmpty(),
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
        price = "${price ?: 0} zł".replace('.', ','),
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
        price = "${price ?: 0} zł".replace('.', ','),
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

fun BiedronkaProductIdPage.mapToDomain(): ProductIdPage {
    return ProductIdPage(
        productIdList = productIdList?.map {
            it.substringAfter("id,")
                .substringBefore(",name")
        } ?: emptyList(),
        pageCount = pages?.last()?.toInt() ?: 1
    )
}

fun BiedronkaProduct.mapToDomain(): Product {
    val title = title?.split(" ")
    return Product(
        id = toString().hashCode(),
        subtitle = title?.subList(0, title.size / 2)?.joinToString(separator = " ").orEmpty(),
        title = title?.subList(title.size / 2, title.size)?.joinToString(separator = " ").orEmpty(),
        oldPrice = "",
        price = "${priceZloty ?: "0"},${priceCents ?: "0"} zł",
        imageUrl = imageUrl?.replace("4x2", "1x1").orEmpty(),
        quantity = quantity?.substringAfter("/").orEmpty(),
        market = Market.BIEDRONKA
    )
}

fun Product.mapToService(): ProductService {
    return ProductService(
        id,
        subtitle,
        title,
        price,
        imageUrl,
        quantity,
        market.name
    )
}