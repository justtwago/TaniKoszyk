package com.github.justtwago.tanikoszyk.services

import com.github.justtwago.tanikoszyk.common.extensions.Response
import com.github.justtwago.tanikoszyk.model.biedronka.BiedronkaProduct
import com.github.justtwago.tanikoszyk.model.domain.Product
import com.github.justtwago.tanikoszyk.model.domain.ProductPage
import com.github.justtwago.tanikoszyk.model.domain.mapToDomain
import com.github.justtwago.tanikoszyk.services.auchan.AuchanRepository
import com.github.justtwago.tanikoszyk.services.biedronka.BiedronkaRepository
import com.github.justtwago.tanikoszyk.services.kaufland.KauflandRepository
import com.github.justtwago.tanikoszyk.services.tesco.TescoRepository


interface MarketsRepository {
    suspend fun getProducts(searchQuery: String, page: Int = 1): ProductPage
}

class MarketsRepositoryImpl(
        private val auchanRepository: AuchanRepository,
        private val kauflandRepository: KauflandRepository,
        private val tescoRepository: TescoRepository,
        private val biedronkaRepository: BiedronkaRepository
) : MarketsRepository {

    override suspend fun getProducts(searchQuery: String, page: Int): ProductPage {
        val biedronkaResponse = biedronkaRepository.getProducts(searchQuery, page)
        val auchanResponse = auchanRepository.getProducts(searchQuery, page)
        val kauflandResponse = kauflandRepository.getProducts(searchQuery, page)
        val tescoResponse = tescoRepository.getProducts(searchQuery, page)

        val biedronkaProducts = when (biedronkaResponse) {
            is Response.Success.WithBody -> {
                val productLinks = biedronkaResponse.body.mapToDomain()
                ProductPage(
                    products = productLinks.productIdList.mapNotNull {
                        val productResponse = biedronkaRepository.getProduct(it)
                        if (productResponse is Response.Success.WithBody) {
                            productResponse.body.mapToDomain()
                        } else null
                    }.filter { it.isNotEmpty() },
                    pageCount = productLinks.pageCount
                )
            }
            else -> null
        }

        val auchanProducts = when (auchanResponse) {
            is Response.Success.WithBody -> auchanResponse.body.mapToDomain()
            else -> null
        }

        val kauflandProducts = when (kauflandResponse) {
            is Response.Success.WithBody -> kauflandResponse.body.mapToDomain()
            else -> null
        }

        val tescoProducts = when (tescoResponse) {
            is Response.Success.WithBody -> tescoResponse.body.mapToDomain()
            else -> null
        }

        val allProductsList = mutableListOf<Product>().apply {
            addAll(biedronkaProducts?.products.orEmpty())
            addAll(auchanProducts?.products.orEmpty())
            addAll(kauflandProducts?.products.orEmpty())
            addAll(tescoProducts?.products.orEmpty())
        }

        return ProductPage(
            products = allProductsList,
            pageCount = getMaxPageCount(
                biedronkaProducts,
                auchanProducts,
                kauflandProducts,
                tescoProducts
            )
        )
    }

    private fun getMaxPageCount(vararg productPage: ProductPage?): Int {
        return productPage.map { it?.pageCount ?: 1 }.max() ?: 1
    }
}