package com.github.justtwago.tanikoszyk.model.kaufland

import pl.droidsonroids.jspoon.annotation.Selector


data class KauflandProductPage(
        @Selector(".t-search-result__list-item") var products: List<KauflandProduct> = emptyList()
)