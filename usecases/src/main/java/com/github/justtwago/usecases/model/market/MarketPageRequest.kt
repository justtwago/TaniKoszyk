package com.github.justtwago.usecases.model.market

import com.github.justtwago.usecases.model.market.common.SortType


data class MarketPageRequest(
        val searchQuery: String,
        val page: Int,
        val sortType: SortType
)