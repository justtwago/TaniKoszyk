package com.tanikoszyk.storage.model

import com.tanikoszyk.domain.Market
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.domain.Money
import com.tanikoszyk.domain.Product

internal fun MarketProduct.toEntity() = ProductEntity(
    url = product.url,
    subtitle = product.subtitle,
    title = product.title,
    price = product.price.toEntity(),
    imageUrl = product.imageUrl,
    quantity = product.quantity,
    market = market.toEntity()
)

internal fun Money.toEntity() = MoneyEntity(
    value = value,
    currency = currency
)

internal fun Market.toEntity() = MarketEntity.valueOf(name)

internal fun ProductEntity.toDomain() = MarketProduct(
    product = Product(
        url = url,
        subtitle = subtitle,
        title = title,
        price = price.toDomain(),
        imageUrl = imageUrl,
        quantity = quantity
    ),
    market = market.toDomain()
)

internal fun MoneyEntity.toDomain() = Money(
    value = value,
    currency = currency
)

internal fun MarketEntity.toDomain() = Market.valueOf(name)