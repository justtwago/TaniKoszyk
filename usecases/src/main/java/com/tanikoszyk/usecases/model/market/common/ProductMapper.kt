package com.tanikoszyk.usecases.model.market.common

import com.tanikoszyk.service.model.auchan.AuchanProduct
import com.tanikoszyk.service.model.auchan.AuchanProductPage
import com.tanikoszyk.service.model.biedronka.BiedronkaProduct
import com.tanikoszyk.service.model.biedronka.BiedronkaProductIdPage
import com.tanikoszyk.service.model.kaufland.KauflandProduct
import com.tanikoszyk.service.model.kaufland.KauflandProductPage
import com.tanikoszyk.service.model.service.ProductService
import com.tanikoszyk.service.model.tesco.TescoProduct
import com.tanikoszyk.service.model.tesco.TescoProductPage
import com.tanikoszyk.service.repositories.AUCHAN_BASE_URL
import com.tanikoszyk.service.repositories.BIEDRONKA_BASE_URL
import com.tanikoszyk.service.repositories.KAUFLAND_BASE_URL
import com.tanikoszyk.service.repositories.TESCO_BASE_URL
import com.tanikoszyk.service.services.BIEDRONKA_SINGLE_PRODUCT_REQUEST

fun AuchanProductPage.mapToDomain(): ProductPage {
    return ProductPage(
        products = products?.map { it.mapToDomain() }?.filter { it.isNotEmpty() }.orEmpty(),
        pageCount = size?.toInt() ?: 0
    )
}

fun AuchanProduct.mapToDomain(): Product {
    return Product(
        url = url?.let { AUCHAN_BASE_URL + it }.orEmpty(),
        subtitle = subtitle.orEmpty(),
        title = title?.substringAfter("$subtitle - ").orEmpty(),
        price = "${priceZloty ?: "0"},${priceCents ?: "0"} zł",
        imageUrl = "$AUCHAN_BASE_URL${imageUrl?.substring(1, imageUrl?.length ?: 0).orEmpty()}",
        quantity = quantity.orEmpty(),
        market = Market.AUCHAN,
        isSelected = false
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
        url = url?.let { KAUFLAND_BASE_URL + it }.orEmpty(),
        subtitle = subtitle.orEmpty(),
        title = title.orEmpty(),
        price = "${price ?: 0} zł".replace('.', ','),
        imageUrl = imageUrl?.split(", ")?.last()?.substringBefore(" ").orEmpty(),
        quantity = quantity.orEmpty(),
        market = Market.KAUFLAND,
        isSelected = false
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
        url = url?.let { TESCO_BASE_URL + it }.orEmpty(),
        subtitle = title?.subList(0, title.size / 2)?.joinToString(separator = " ").orEmpty(),
        title = title?.subList(title.size / 2, title.size)?.joinToString(separator = " ").orEmpty(),
        price = "${price ?: 0} zł".replace('.', ','),
        imageUrl = imageUrl.orEmpty(),
        quantity = mapQuantity(),
        market = Market.TESCO,
        isSelected = false
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

fun BiedronkaProduct.mapToDomain(url: String): Product {
    val title = title?.split(" ")
    return Product(
        url = BIEDRONKA_BASE_URL + BIEDRONKA_SINGLE_PRODUCT_REQUEST + url,
        subtitle = title?.subList(0, title.size / 2)?.joinToString(separator = " ").orEmpty(),
        title = title?.subList(title.size / 2, title.size)?.joinToString(separator = " ").orEmpty(),
        price = "${priceZloty ?: "0"},${priceCents ?: "0"} zł",
        imageUrl = imageUrl?.replace("4x2", "1x1").orEmpty(),
        quantity = quantity?.substringAfter("/").orEmpty(),
        market = Market.BIEDRONKA,
        isSelected = false
    )
}

fun Product.mapToService(): ProductService {
    return ProductService(
        id,
        url,
        subtitle,
        title,
        price,
        imageUrl,
        quantity,
        market.name
    )
}

fun ProductService.mapToDomain(): Product {
    return Product(
        url,
        subtitle,
        title,
        price,
        imageUrl,
        quantity,
        Market.valueOf(market),
        false
    )
}