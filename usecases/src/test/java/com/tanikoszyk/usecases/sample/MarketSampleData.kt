package com.tanikoszyk.usecases.sample

import com.tanikoszyk.service.model.auchan.AuchanProduct
import com.tanikoszyk.service.model.auchan.AuchanProductPage
import com.tanikoszyk.service.model.biedronka.BiedronkaProduct
import com.tanikoszyk.service.model.biedronka.BiedronkaProductIdPage
import com.tanikoszyk.service.model.kaufland.KauflandProduct
import com.tanikoszyk.service.model.kaufland.KauflandProductPage
import com.tanikoszyk.service.model.tesco.TescoProduct
import com.tanikoszyk.service.model.tesco.TescoProductPage
import com.tanikoszyk.usecases.model.market.MarketPageRequest
import com.tanikoszyk.usecases.model.market.common.Market
import com.tanikoszyk.usecases.model.market.common.Product
import com.tanikoszyk.usecases.model.market.common.ProductPage
import com.tanikoszyk.usecases.model.market.common.SortType


val marketPageRequest = MarketPageRequest(
    searchQuery = "searchQuery",
    page = 0,
    sortType = SortType.TARGET
)

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


val product = Product(
    id = 0,
    subtitle = "subtitle",
    title = "title",
    price = "1,99 zł",
    imageUrl = "image",
    quantity = "1 kg",
    market = Market.AUCHAN,
    oldPrice = ""
)

val productAuchan = product.copy(
    id = auchanProduct.hashCode(),
    market = Market.AUCHAN,
    imageUrl = "https://www.auchandirect.pl/image"
)


val productBiedronka = product.copy(
    id = biedronkaProduct.hashCode(),
    market = Market.BIEDRONKA
)


val productKaufland = product.copy(
    id = kauflandProduct.hashCode(),
    market = Market.KAUFLAND
)


val productTesco = product.copy(
    id = tescoProduct.hashCode(),
    market = Market.TESCO
)

val productPageAuchan = ProductPage(
    products = listOf(productAuchan, productAuchan), pageCount = 1
)

val productPageBiedronka = ProductPage(
    products = listOf(productBiedronka, productBiedronka), pageCount = 1
)

val productPageKaufland = ProductPage(
    products = listOf(productKaufland, productKaufland), pageCount = 1
)

val productPageTesco = ProductPage(
    products = listOf(productTesco, productTesco), pageCount = 1
)