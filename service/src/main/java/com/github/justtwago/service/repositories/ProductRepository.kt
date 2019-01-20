package com.github.justtwago.service.repositories

import com.github.justtwago.service.common.Response
import com.github.justtwago.service.model.domain.mapToDomain
import com.github.justtwago.service.model.domain.Product
import com.github.justtwago.service.model.domain.ProductPage


interface ProductRepository {
    suspend fun getProducts(searchQuery: String, page: Int = 1): ProductPage
}

class ProductRepositoryImpl(
        private val auchanRepository: AuchanRepository,
        private val kauflandRepository: KauflandRepository,
        private val tescoRepository: TescoRepository,
        private val biedronkaRepository: BiedronkaRepository
) : ProductRepository {

    override suspend fun getProducts(searchQuery: String, page: Int): ProductPage {
        val biedronkaResponse = biedronkaRepository.getProducts(searchQuery, page)
        val auchanResponse = auchanRepository.getProducts(searchQuery, page)
        val kauflandResponse = kauflandRepository.getProducts(searchQuery, page)
        val tescoResponse = tescoRepository.getProducts(searchQuery, page)

        val biedronkaProducts = when (biedronkaResponse) {
            is Response.Success.WithBody -> {
                val productLinks = biedronkaResponse.body.mapToDomain()
                if (productLinks.pageCount >= page) {
                    ProductPage(
                        products = productLinks.productIdList.mapNotNull {
                            val productResponse = biedronkaRepository.getProduct(it)
                            if (productResponse is Response.Success.WithBody) {
                                productResponse.body.mapToDomain()
                            } else null
                        }.filter { it.isNotEmpty() },
                        pageCount = productLinks.pageCount
                    )
                } else null
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