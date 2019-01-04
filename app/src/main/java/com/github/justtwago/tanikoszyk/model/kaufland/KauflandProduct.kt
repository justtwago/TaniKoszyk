package com.github.justtwago.tanikoszyk.model.kaufland

import pl.droidsonroids.jspoon.annotation.Selector

data class KauflandProduct(
        @Selector(".m-offer-tile__subtitle") var subtitle: String = "",
        @Selector(".m-offer-tile__title") var title: String = "",
        @Selector(".a-pricetag__old-price") var oldPrice: String = "",
        @Selector(".a-pricetag__price") var price: String = "",
        @Selector(
            ".m-offer-tile__image > figure > img",
            attr = "data-src"
        ) var imageUrl: String = "",
        @Selector(".m-offer-tile__quantity") var quanity: String = ""
)
