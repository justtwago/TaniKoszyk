package com.github.justtwago.tanikoszyk.model.auchan

import pl.droidsonroids.jspoon.annotation.Selector

data class AuchanProduct(
        @Selector(".about > strong") var subtitle: String? = null,
        @Selector(".about") var title: String? = null,
        @Selector(".p-nb") var priceZloty: String? = null,
        @Selector(".p-cents") var priceCents: String? = null,
        @Selector(".picture > img", attr = "data-src") var imageUrl: String ? = null,
        @Selector(".packaging > strong") var quantity: String? = null
)
