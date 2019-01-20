package com.github.justtwago.service

import com.github.justtwago.service.model.auchan.AuchanProduct
import com.github.justtwago.service.model.auchan.AuchanProductPage
import com.github.justtwago.service.model.biedronka.BiedronkaProduct
import com.github.justtwago.service.model.biedronka.BiedronkaProductIdPage
import com.github.justtwago.service.model.kaufland.KauflandProduct
import com.github.justtwago.service.model.kaufland.KauflandProductPage
import com.github.justtwago.service.model.tesco.TescoProduct
import com.github.justtwago.service.model.tesco.TescoProductPage

const val biedronkaProductId = "id"

val auchanProduct = AuchanProduct(
    subtitle = "subtitle",
    title = "title",
    priceZloty = "1",
    priceCents = "99",
    imageUrl = "/image",
    quantity = "1 kg"
)

val kauflandProduct = KauflandProduct(
    subtitle = "subtitle",
    title = "title",
    oldPrice = "1",
    price = "1.99",
    imageUrl = "image",
    quantity = "1 kg"
)

val biedronkaProduct = BiedronkaProduct(
    title = "subtitle title",
    priceZloty = "1",
    priceCents = "99",
    imageUrl = "image",
    quantity = "1 kg"
)

val tescoProduct = TescoProduct(
    subtitle = "subtitle title",
    title = "subtitle title",
    price = "1,99",
    priceKg = "1",
    weight = "1 kg",
    imageUrl = "image",
    quantity = ""
)

val auchanProductPage = AuchanProductPage(
    products = listOf(auchanProduct, auchanProduct), size = "1"
)

val kauflandProductPage = KauflandProductPage(
    products = listOf(kauflandProduct, kauflandProduct), size = "1"
)

val biedronkaProductIdPage = BiedronkaProductIdPage(
    productIdList = listOf(biedronkaProductId, biedronkaProductId), pages = listOf("1")
)

val tescoProductPage = TescoProductPage(
    products = listOf(tescoProduct, tescoProduct), size = "1"
)