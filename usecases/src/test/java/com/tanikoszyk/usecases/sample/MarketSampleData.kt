package com.tanikoszyk.usecases.sample

import com.fanmountain.domain.*
import com.tanikoszyk.service.model.data.auchan.AuchanProduct
import com.tanikoszyk.service.model.data.auchan.AuchanProductPage
import com.tanikoszyk.service.model.data.biedronka.BiedronkaProduct
import com.tanikoszyk.service.model.data.biedronka.BiedronkaProductIdPage
import com.tanikoszyk.service.model.data.kaufland.KauflandProduct
import com.tanikoszyk.service.model.data.kaufland.KauflandProductPage
import com.tanikoszyk.usecases.requests.MarketPageRequest

val marketPageRequest = MarketPageRequest(
    searchQuery = "searchQuery",
    page = 0,
    sortType = SortType.TARGET
)

const val biedronkaProductId = "id"

val auchanProduct = AuchanProduct(
    url = "url",
    subtitle = "subtitle",
    title = "title",
    priceZloty = "1",
    priceCents = "99",
    imageUrl = "/image",
    quantity = "1 kg"
)

val kauflandProduct = KauflandProduct(
    url = "url",
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

val auchanProductPage = AuchanProductPage(
    products = listOf(auchanProduct, auchanProduct), size = "1"
)

val kauflandProductPage = KauflandProductPage(
    products = listOf(kauflandProduct, kauflandProduct), size = "1"
)

val biedronkaProductIdPage = BiedronkaProductIdPage(
    productIdList = listOf(biedronkaProductId, biedronkaProductId), pages = listOf("1")
)

val product = Product(
    url = "url",
    subtitle = "subtitle",
    title = "title",
    price = "1,99 zł",
    imageUrl = "image",
    quantity = "1 kg"
)

val productAuchan = MarketProduct(
    product = product.copy(
        url = "https://www.auchandirect.pl/url",
        imageUrl = "https://www.auchandirect.pl/image"
    ),
    market = Market.AUCHAN
)

val productBiedronka = MarketProduct(
    product = product.copy(
        url = "http://www.biedronka.pl/pl/product,id,id"
    ),
    market = Market.BIEDRONKA
)

val productKaufland = MarketProduct(
    product = product.copy(
        url = "https://www.kaufland.pl/url"
    ),
    market = Market.KAUFLAND
)

val productPageAuchan = ProductPage(
    marketProducts = listOf(productAuchan, productAuchan), pageCount = 1
)

val productPageBiedronka = ProductPage(
    marketProducts = listOf(productBiedronka, productBiedronka), pageCount = 1
)

val productPageKaufland = ProductPage(
    marketProducts = listOf(productKaufland, productKaufland), pageCount = 1
)
