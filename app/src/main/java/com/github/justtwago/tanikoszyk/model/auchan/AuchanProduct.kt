package com.github.justtwago.tanikoszyk.model.auchan

import pl.droidsonroids.jspoon.annotation.Selector

data class AuchanProduct(
        @Selector(".about > strong") var subtitle: String = "",
        @Selector(".about") var title: String = "",
        @Selector(".p-nb") var priceZloty: String = "",
        @Selector(".p-cents") var priceCents: String = "",
        @Selector(".picture > img", attr = "data-src") var imageUrl: String = "",
        @Selector(".packaging > strong") var quanity: String = ""
)
