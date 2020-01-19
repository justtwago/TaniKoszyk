package com.tanikoszyk.storage.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class ProductEntity(
    @PrimaryKey val url: String,
    val title: String,
    val subtitle: String,
    val quantity: String,
    val market: MarketEntity,
    @Embedded val price: MoneyEntity,
    @ColumnInfo(name = "image_url") val imageUrl: String
)

@Entity
internal data class MoneyEntity(
    @ColumnInfo(name = "value") val value: Double,
    @ColumnInfo(name = "currency") val currency: String
)

internal enum class MarketEntity {
    AUCHAN,
    BIEDRONKA,
    KAUFLAND
}