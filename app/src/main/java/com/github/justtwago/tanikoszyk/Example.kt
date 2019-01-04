package com.github.justtwago.tanikoszyk

import pl.droidsonroids.jspoon.annotation.Selector

data class ProductPage(
        @Selector(".t-search-result__list-item") var products: List<Product> = emptyList()
)

data class Product(
        @Selector(".m-offer-tile__subtitle") var subtitle: String = "",
        @Selector(".m-offer-tile__title") var title: String = "",
        @Selector(".a-pricetag__price") var price: String = "",
        @Selector(
            ".m-offer-tile__image > figure > img",
            attr = "data-src"
        ) var imageUrl: List<String> = emptyList(),
        @Selector(".m-offer-tile__quantity") var quanity: String = ""
)