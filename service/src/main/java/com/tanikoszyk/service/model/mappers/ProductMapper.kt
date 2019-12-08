package com.tanikoszyk.service.model.mappers

import com.tanikoszyk.domain.Market
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.domain.Product
import com.tanikoszyk.domain.ProductPage
import com.tanikoszyk.service.model.data.ProductIdPage
import com.tanikoszyk.service.model.data.auchan.AuchanProduct
import com.tanikoszyk.service.model.data.auchan.AuchanProductPage
import com.tanikoszyk.service.model.data.biedronka.BiedronkaProduct
import com.tanikoszyk.service.model.data.biedronka.BiedronkaProductIdPage
import com.tanikoszyk.service.model.data.kaufland.KauflandProduct
import com.tanikoszyk.service.model.data.kaufland.KauflandProductPage
import com.tanikoszyk.service.repositories.AUCHAN_BASE_URL
import com.tanikoszyk.service.repositories.BIEDRONKA_BASE_URL
import com.tanikoszyk.service.repositories.KAUFLAND_BASE_URL
import com.tanikoszyk.service.services.BIEDRONKA_SINGLE_PRODUCT_REQUEST

internal fun AuchanProductPage.mapToDomain(): ProductPage {
    return ProductPage(
        marketProducts = products?.map { it.mapToDomain() }.orEmpty(),
        pageCount = size?.toInt() ?: 0
    )
}

internal fun AuchanProduct.mapToDomain() = MarketProduct(
    product = Product(
        url = url?.let { AUCHAN_BASE_URL + it }.orEmpty(),
        subtitle = subtitle.orEmpty(),
        title = title?.substringAfter("$subtitle - ").orEmpty(),
        price = "${priceZloty ?: "0"},${priceCents ?: "0"} zł",
        imageUrl = "$AUCHAN_BASE_URL${imageUrl?.substring(1, imageUrl?.length ?: 0).orEmpty()}",
        quantity = quantity.orEmpty()
    ),
    market = Market.AUCHAN
)

internal fun KauflandProductPage.mapToDomain(): ProductPage {
    return ProductPage(
        marketProducts = products?.map { it.mapToDomain() }.orEmpty(),
        pageCount = size?.mapToKauflandPageCountDomain() ?: 0
    )
}

internal fun KauflandProduct.mapToDomain() = MarketProduct(
    product = Product(
        url = url?.let { KAUFLAND_BASE_URL + it }.orEmpty(),
        subtitle = subtitle.orEmpty(),
        title = title.orEmpty(),
        price = "${price ?: 0} zł".replace('.', ','),
        imageUrl = imageUrl?.split(", ")?.last()?.substringBefore(" ").orEmpty(),
        quantity = quantity.orEmpty()
    ),
    market = Market.KAUFLAND
)

private fun String.mapToKauflandPageCountDomain(): Int {
    val productCount = substringAfter("(").substringBefore(")").toInt()
    var pageCount = productCount / Market.KAUFLAND.pageSize
    if (productCount % Market.KAUFLAND.pageSize != 0) pageCount++
    return pageCount
}

internal fun BiedronkaProductIdPage.mapToDomain(): ProductIdPage {
    return ProductIdPage(
        productIdList = productIdList?.map {
            it.substringAfter("id,")
                .substringBefore(",name")
        } ?: emptyList(),
        pageCount = pages?.last()?.toInt() ?: 1
    )
}

internal fun BiedronkaProduct.mapToDomain(url: String): MarketProduct {
    val title = title?.split(" ")
    return MarketProduct(
        product = Product(
            url = BIEDRONKA_BASE_URL + BIEDRONKA_SINGLE_PRODUCT_REQUEST + url,
            subtitle = title?.subList(0, title.size / 2)?.joinToString(separator = " ").orEmpty(),
            title = title?.subList(title.size / 2, title.size)?.joinToString(separator = " ").orEmpty(),
            price = "${priceZloty ?: "0"},${priceCents ?: "0"} zł",
            imageUrl = imageUrl?.replace("4x2", "1x1").orEmpty(),
            quantity = quantity?.substringAfter("/").orEmpty()
        ),
        market = Market.BIEDRONKA
    )
}
