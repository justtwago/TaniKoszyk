package com.github.justtwago.tanikoszyk.model.tesco

import pl.droidsonroids.jspoon.annotation.Selector


data class TescoProduct(
        @Selector(".product-tile--browsable") var subtitle: String? = null,
        @Selector(".product-tile--browsable") var title: String? = null,
        @Selector(".price-per-sellable-unit > div > span > .value") var price: String? = null,
        @Selector(".price-per-quantity-weight > span > .value") var priceKg: String? = null,
        @Selector(".product-image__container > img", attr = "src") var imageUrl: String? = null,
        @Selector(".product-input", attr = "value") var quantity: String? = null,
        @Selector(".weightedProduct-text") var weight: String? = null
)