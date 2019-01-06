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


interface BaseRepository {
    suspend fun getProducts(searchQuery: String): ProductPage
}

class BaseRepositoryImpl(
        private val auchanRepository: AuchanRepository,
        private val kauflandRepository: KauflandRepository,
        private val tescoRepository: TescoRepository
) : BaseRepository {


    override suspend fun getProducts(searchQuery: String): ProductPage {
        val auchanResponse: Response<AuchanProductPage> = auchanRepository.getProducts(searchQuery)
        val kauflandResponse: Response<KauflandProductPage> = kauflandRepository.getProducts(
            searchQuery,
            1
        )
        val tescoResponse: Response<TescoProductPage> = tescoRepository.getProducts(searchQuery, 1)

        val auchanProducts: ProductPage? = when (auchanResponse) {
            is Response.Success.WithBody -> auchanResponse.body.mapToDomain()
            else -> null
        }

        val kauflandProducts: ProductPage? = when (kauflandResponse) {
            is Response.Success.WithBody -> kauflandResponse.body.mapToDomain()
            else -> null
        }

        val tescoProducts: ProductPage? = when (tescoResponse) {
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
            pageSize = 1,
            pageCount = max(
                max(auchanProducts?.pageCount ?: 0, kauflandProducts?.pageCount ?: 0),
                tescoProducts?.pageCount ?: 0
            )
        )
    }
}