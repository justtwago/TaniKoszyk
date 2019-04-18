package com.tanikoszyk.ui.home.list

import com.tanikoszyk.usecases.model.market.common.Market
import com.tanikoszyk.usecases.model.market.common.Product

class SearchProductItemViewModel(
    val id: Int,
    val subtitle: String,
    val title: String,
    val price: String,
    val imageUrl: String,
    val quantity: String,
    val market: Market,
    val onClickListener: (Product) -> Boolean,
    var isInCart: Boolean
) {
    fun onProductClicked() {
        isInCart = onClickListener.invoke(
            Product(id, subtitle, title, price, imageUrl, quantity, market)
        )
    }
}