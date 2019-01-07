package com.github.justtwago.tanikoszyk.model.kaufland

import pl.droidsonroids.jspoon.annotation.Selector


data class KauflandProductPage(
        @Selector(".t-search-result__list-item") var products: List<KauflandProduct>? = null,
        @Selector(".t-search-result__result-size") var size: String? = null
)