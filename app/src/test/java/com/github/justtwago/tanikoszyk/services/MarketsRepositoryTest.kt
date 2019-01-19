package com.github.justtwago.tanikoszyk.services

import com.github.justtwago.tanikoszyk.*
import com.github.justtwago.tanikoszyk.common.extensions.Response
import com.github.justtwago.tanikoszyk.model.domain.Market
import com.github.justtwago.tanikoszyk.services.auchan.AUCHAN_BASE_URL
import com.github.justtwago.tanikoszyk.services.auchan.AuchanRepository
import com.github.justtwago.tanikoszyk.services.biedronka.BiedronkaRepository
import com.github.justtwago.tanikoszyk.services.kaufland.KauflandRepository
import com.github.justtwago.tanikoszyk.services.tesco.TescoRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MarketsRepositoryImplTest {

    @Mock lateinit var auchanRepository: AuchanRepository
    @Mock lateinit var kauflandRepository: KauflandRepository
    @Mock lateinit var tescoRepository: TescoRepository
    @Mock lateinit var biedronkaRepository: BiedronkaRepository
    @InjectMocks lateinit var marketsRepositoryImpl: MarketsRepositoryImpl


    @Test
    fun `get all products from auchan, kaufland, tesco and biedronka markets`() = runBlocking {
        val searchQuery = "product"
        val page = 0
        whenever(biedronkaRepository.getProducts(searchQuery, page)).thenReturn(
            Response.Success.WithBody(biedronkaProductIdPage)
        )
        whenever(biedronkaRepository.getProduct(biedronkaProductId)).thenReturn(
            Response.Success.WithBody(biedronkaProduct)
        )
        whenever(auchanRepository.getProducts(searchQuery = searchQuery, page = page)).thenReturn(
            Response.Success.WithBody(auchanProductPage)
        )
        whenever(kauflandRepository.getProducts(searchQuery = searchQuery, page = page)).thenReturn(
            Response.Success.WithBody(kauflandProductPage)
        )
        whenever(tescoRepository.getProducts(searchQuery = searchQuery, page = page)).thenReturn(
            Response.Success.WithBody(tescoProductPage)
        )

        val productPage = marketsRepositoryImpl.getProducts(searchQuery = searchQuery, page = page)

        assertThat(productPage.pageCount).isEqualTo(1)
        assertThat(productPage.products.size).isEqualTo(
            biedronkaProductIdPage.productIdList!!.size
                    + auchanProductPage.products!!.size
                    + kauflandProductPage.products!!.size
                    + tescoProductPage.products!!.size
        )
        productPage.products.forEach {
            assertThat(it.id).isEqualTo(
                when (it.market) {
                    Market.AUCHAN -> auchanProduct.toString().hashCode()
                    Market.BIEDRONKA -> biedronkaProduct.toString().hashCode()
                    Market.KAUFLAND -> kauflandProduct.toString().hashCode()
                    Market.TESCO -> tescoProduct.toString().hashCode()
                }
            )
            assertThat(it.subtitle).isEqualTo("subtitle")
            assertThat(it.title).isEqualTo("title")
            assertThat(it.price).isEqualTo("1,99 zł")
            assertThat(it.quantity).isEqualTo("1 kg")
            assertThat(it.imageUrl).isEqualTo(
                when (it.market) {
                    Market.AUCHAN -> "https://www.auchandirect.pl/image"
                    else -> "image"
                }
            )
            assertThat(it.oldPrice).isEqualTo(
                when(it.market) {
                    Market.KAUFLAND -> kauflandProduct.oldPrice
                    else -> ""
                }
            )
        }
    }
}