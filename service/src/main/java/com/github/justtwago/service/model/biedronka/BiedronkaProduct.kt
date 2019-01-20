package com.github.justtwago.service.model.biedronka

import pl.droidsonroids.jspoon.annotation.Selector


data class BiedronkaProduct(
        @Selector(".prod-cat-descryption > h3") var title: String? = null,
        @Selector(".pln") var priceZloty: String? = null,
        @Selector(".gr") var priceCents: String? = null,
        @Selector(".main-pic > img", attr = "src") var imageUrl: String? = null,
        @Selector(".amount") var quantity: String? = null
)