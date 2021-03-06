package com.tanikoszyk.service.model.data.kaufland

import pl.droidsonroids.jspoon.annotation.Selector

internal data class KauflandProduct(
    @Selector(".m-offer-tile__link", attr = "href") var url: String? = null,
    @Selector(".m-offer-tile__subtitle") var subtitle: String? = null,
    @Selector(".m-offer-tile__title") var title: String? = null,
    @Selector(".a-pricetag__old-price") var oldPrice: String? = null,
    @Selector(".a-pricetag__price") var price: String? = null,
    @Selector(
        ".m-offer-tile__image > figure > img",
        attr = "data-srcset"
    ) var imageUrl: String? = null,
    @Selector(".m-offer-tile__quantity") var quantity: String? = null
)
