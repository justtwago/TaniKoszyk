package com.github.justtwago.tanikoszyk.services.base

import com.github.justtwago.tanikoszyk.common.extensions.Response
import com.github.justtwago.tanikoszyk.model.auchan.AuchanProductPage
import com.github.justtwago.tanikoszyk.model.domain.Product
import com.github.justtwago.tanikoszyk.model.domain.ProductPage
import com.github.justtwago.tanikoszyk.model.domain.mapToDomain
import com.github.justtwago.tanikoszyk.model.kaufland.KauflandProductPage
import com.github.justtwago.tanikoszyk.model.tesco.TescoProductPage
import com.github.justtwago.tanikoszyk.services.auchan.AuchanRepository
import com.github.justtwago.tanikoszyk.services.kaufland.KauflandRepository
import com.github.justtwago.tanikoszyk.services.tesco.TescoRepository
import kotlin.math.max


interface MarketsRepository {
    suspend fun getProducts(searchQuery: String, page: Int = 1): ProductPage
}

class MarketsRepositoryImpl(
        private val auchanRepository: AuchanRepository,
        private val kauflandRepository: KauflandRepository,
        private val tescoRepository: TescoRepository
) : MarketsRepository {

    override suspend fun getProducts(searchQuery: String, page: Int): ProductPage {
        val auchanResponse = if (page == 1) {
            auchanRepository.getProducts(searchQuery)
        } else null // TODO: There is no possibility to get next pages from Auchan yet

        val kauflandResponse = kauflandRepository.getProducts(searchQuery, page)
        val tescoResponse = tescoRepository.getProducts(searchQuery, page)

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
            addAll(auchanProducts?.products.orEmpty())
            addAll(kauflandProducts?.products.orEmpty())
            addAll(tescoProducts?.products.orEmpty())
        }

        return ProductPage(
            products = allProductsList,
            pageCount = getMaxPageCount(auchanProducts, kauflandProducts, tescoProducts)
        )
    }

    private fun getMaxPageCount(vararg productPage: ProductPage?): Int {
        return productPage.map { it?.pageCount ?: 1 }.max() ?: 1
    }
}