package com.github.justtwago.service.model.auchan

import pl.droidsonroids.jspoon.annotation.Selector


data class AuchanProductPage(
        @Selector(".product") var products: List<AuchanProduct>? = null,
        @Selector("#loadMoreProductsButton", attr = "data-maxPage") var size: String? = null
)