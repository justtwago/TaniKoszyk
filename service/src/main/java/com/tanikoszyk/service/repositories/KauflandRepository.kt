package com.tanikoszyk.service.repositories

import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.domain.ProductPage
import com.tanikoszyk.domain.Result
import com.tanikoszyk.domain.SortType
import com.tanikoszyk.service.common.execute
import com.tanikoszyk.service.model.mappers.mapToDomain
import com.tanikoszyk.service.services.KauflandService

const val KAUFLAND_BASE_URL = "https://www.kaufland.pl/"

interface KauflandRepository {
    suspend fun getProducts(searchQuery: String, page: Int, sortType: SortType): Result<ProductPage>
}

internal class KauflandRepositoryImpl(private val service: KauflandService) : KauflandRepository {
    override suspend fun getProducts(searchQuery: String, page: Int, sortType: SortType): Result<ProductPage> {
        val productsResult = service.getProducts(searchQuery = searchQuery, page = page - 1).execute {
            it.mapToDomain()
        }
        return when (productsResult) {
            is Result.Success.WithBody -> {
                val sortedProducts = productsResult.body.marketProducts.sort(sortType)
                Result.Success.WithBody(productsResult.body.copy(marketProducts = sortedProducts))
            }
            is Result.Success.Empty -> Result.Failure(IllegalStateException("Page shouldn't be empty"))
            is Result.Failure -> Result.Failure(productsResult.throwable)
        }
    }

    private fun List<MarketProduct>.sort(sortType: SortType): List<MarketProduct> {
        return when (sortType) {
            SortType.TARGET -> this
            SortType.ALPHABETICAL_ASCEND -> sortedBy { it.product.title }
            SortType.ALPHABETICAL_DESCEND -> sortedByDescending { it.product.title }
            SortType.PRICE_ASCEND -> sortedBy {
                it.product.price.substringBefore(" ").replace(',', '.').toDouble()
            }
            SortType.PRICE_DESCEND -> sortedByDescending {
                it.product.price.substringBefore(" ").replace(',', '.').toDouble()
            }
        }
    }
}