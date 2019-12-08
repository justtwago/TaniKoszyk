package com.tanikoszyk.service.model.data.kaufland

import pl.droidsonroids.jspoon.annotation.Selector

internal data class KauflandProductPage(
    @Selector(".t-search-result__list-item") var products: List<KauflandProduct>? = null,
    @Selector(".t-search-result__result-count") var size: String? = null
)