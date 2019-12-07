package com.tanikoszyk.usecases.requests

import com.fanmountain.domain.SortType

data class MarketPageRequest(
    val searchQuery: String,
    val page: Int,
    val sortType: SortType
)