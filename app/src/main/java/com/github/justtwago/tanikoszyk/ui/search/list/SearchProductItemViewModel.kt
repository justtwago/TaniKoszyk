package com.github.justtwago.tanikoszyk.ui.search.list

import com.github.justtwago.service.model.domain.Market


class SearchProductItemViewModel(
        val id: Int,
        val subtitle: String,
        val title: String,
        val oldPrice: String,
        val price: String,
        val imageUrl: String,
        val quantity: String,
        val market: Market
)