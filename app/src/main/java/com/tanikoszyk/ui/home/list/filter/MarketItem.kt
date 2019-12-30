package com.tanikoszyk.ui.home.list.filter

import com.tanikoszyk.domain.Market

data class MarketItem(
    val market: Market,
    var isChecked: Boolean
) {

    @ExperimentalStdlibApi val formattedName: String get() = market.name.toLowerCase().capitalize()
}