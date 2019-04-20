package com.tanikoszyk.usecases.model.market

import com.tanikoszyk.usecases.model.market.common.SortType


data class MarketPageRequest(
        val searchQuery: String,
        val page: Int,
        val sortType: SortType
)