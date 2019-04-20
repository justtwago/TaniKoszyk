package com.tanikoszyk.ui.home.list

import com.tanikoszyk.usecases.model.market.common.Market
import com.tanikoszyk.usecases.model.market.common.Product

data class SearchProductItemViewModel(
    val url: String,
    val subtitle: String,
    val title: String,
    val price: String,
    val imageUrl: String,
    val quantity: String,
    val market: Market,
    val isSelected: Boolean
) {

    val product: Product
        get() = Product(url, subtitle, title, price, imageUrl, quantity, market, isSelected)
}