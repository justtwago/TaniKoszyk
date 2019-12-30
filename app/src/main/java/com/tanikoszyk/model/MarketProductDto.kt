package com.tanikoszyk.model

import android.os.Parcelable
import com.tanikoszyk.domain.Market
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.domain.Money
import com.tanikoszyk.domain.Product
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarketProductDto(
    val product: ProductDto,
    val market: Market
) : Parcelable

@Parcelize
data class ProductDto(
    val url: String,
    val subtitle: String,
    val title: String,
    val price: MoneyDto,
    val imageUrl: String,
    val quantity: String
) : Parcelable

@Parcelize
data class MoneyDto(
    val value: Double,
    val currency: String
) : Parcelable

fun MarketProduct.toDto() = MarketProductDto(
    product = product.toDto(),
    market = market
)

fun Product.toDto() = ProductDto(
    url = url,
    subtitle = subtitle,
    title = title,
    price = price.toDto(),
    imageUrl = imageUrl,
    quantity = quantity
)

fun Money.toDto() = MoneyDto(
    value = value,
    currency = currency
)

fun MarketProductDto.toDomain() = MarketProduct(
    product = product.toDomain(),
    market = market
)

fun ProductDto.toDomain() = Product(
    url = url,
    subtitle = subtitle,
    title = title,
    price = price.toDomain(),
    imageUrl = imageUrl,
    quantity = quantity
)

fun MoneyDto.toDomain() = Money(
    value = value,
    currency = currency
)