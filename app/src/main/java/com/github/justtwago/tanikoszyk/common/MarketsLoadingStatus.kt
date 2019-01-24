package com.github.justtwago.tanikoszyk.common


data class MarketsLoadingStatus(
        var isAuchanProductsReady: Boolean = true,
        var isBiedronkaProductsReady: Boolean = true,
        var isKauflandProductsReady: Boolean = true,
        var isTescoProductsReady: Boolean = true
) {

    fun isContentReady() = isAuchanProductsReady
            && isBiedronkaProductsReady
            && isKauflandProductsReady
            && isTescoProductsReady
}