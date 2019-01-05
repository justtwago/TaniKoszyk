package com.github.justtwago.tanikoszyk.model.auchan

import pl.droidsonroids.jspoon.annotation.Selector


data class AuchanProductPage(
        @Selector(".product-list > .product") var products: List<AuchanProduct>? = null
)