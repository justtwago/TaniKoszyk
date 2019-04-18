package com.tanikoszyk.service.model.tesco

import pl.droidsonroids.jspoon.annotation.Selector


data class TescoProductPage(
        @Selector(".product-list--list-item") var products: List<TescoProduct>? = null,
        @Selector(".items-count__filter-caption") var size: String? = null
)