package com.tanikoszyk.common

data class MarketsLoadingStatus(
    var isAuchanProductsReady: Boolean = true,
    var isBiedronkaProductsReady: Boolean = true,
    var isKauflandProductsReady: Boolean = true
) {

    fun isContentReady() = isAuchanProductsReady
            && isBiedronkaProductsReady
            && isKauflandProductsReady
}