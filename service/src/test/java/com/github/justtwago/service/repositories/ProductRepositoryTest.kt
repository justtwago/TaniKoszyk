package com.github.justtwago.service.repositories

import com.github.justtwago.service.*
import com.github.justtwago.service.common.Response
import com.github.justtwago.service.model.domain.Market
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductRepositoryImplTest {

    @Mock lateinit var auchanRepository: AuchanRepository
    @Mock lateinit var kauflandRepository: KauflandRepository
    @Mock lateinit var tescoRepository: TescoRepository
    @Mock lateinit var biedronkaRepository: BiedronkaRepository
    @InjectMocks lateinit var marketsRepositoryImpl: ProductRepositoryImpl


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