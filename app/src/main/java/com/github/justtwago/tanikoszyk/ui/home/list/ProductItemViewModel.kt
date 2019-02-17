package com.github.justtwago.tanikoszyk.ui.home.list

import com.github.justtwago.usecases.model.market.common.Market


class ProductItemViewModel(
        val id: Int,
        val subtitle: String,
        val title: String,
        val oldPrice: String,
        val price: String,
        val imageUrl: String,
        val quantity: String,
        val market: Market
)