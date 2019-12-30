package com.tanikoszyk.service.repositories

import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.domain.ProductPage
import com.tanikoszyk.domain.Result
import com.tanikoszyk.domain.SortType
import com.tanikoszyk.service.common.execute
import com.tanikoszyk.service.model.data.ProductIdPage
import com.tanikoszyk.service.model.mappers.mapToDomain
import com.tanikoszyk.service.services.BiedronkaService

const val BIEDRONKA_BASE_URL = "http://www.biedronka.pl/"

interface BiedronkaRepository {
    suspend fun getProducts(searchQuery: String, page: Int, sortType: SortType): Result<ProductPage>
}

internal class BiedronkaRepositoryImpl(private val service: BiedronkaService) : BiedronkaRepository {

    override suspend fun getProducts(searchQuery: String, page: Int, sortType: SortType): Result<ProductPage> {
        val productIdsResult = service
            .getProducts(searchQuery = searchQuery, page = page)
            .execute { it.mapToDomain() }

        return when (productIdsResult) {
            is Result.Success.WithBody -> {
                val productPage = getProductPage(productIdsResult.body, page)
                val sortedProducts = productPage?.marketProducts
                    ?.sort(sortType)
                    ?.filter { it.product.isAvailable }
                    .orEmpty()
                productPage?.let {
                    Result.Success.WithBody(it.copy(marketProducts = sortedProducts))
                } ?: Result.Failure(IllegalStateException("Page shouldn't be null"))
            }
            is Result.Success.Empty -> Result.Failure(IllegalStateException("Page shouldn't be empty"))
            is Result.Failure -> Result.Failure(productIdsResult.throwable)
        }
    }

    private suspend fun getProductPage(productLinks: ProductIdPage, page: Int): ProductPage? {
        return if (productLinks.pageCount >= page) {
            ProductPage(
                marketProducts = getProductsFromLinks(productLinks),
                pageCount = productLinks.pageCount
            )
        } else null
    }

    private suspend fun getProductsFromLinks(productLinks: ProductIdPage): List<MarketProduct> {
        return productLinks.productIdList.mapNotNull {
            val result = getProductDetails(it)
            (result as? Result.Success.WithBody)?.body
        }
    }

    private suspend fun getProductDetails(id: String): Result<MarketProduct> {
        return service.getProduct(id = id).execute { it.mapToDomain(id) }
    }

    //TODO: Workaround because there is no way to sort products in website. API needed!
    private fun List<MarketProduct>.sort(sortType: SortType): List<MarketProduct> {
        return when (sortType) {
            SortType.TARGET -> this
            SortType.ALPHABETICAL_ASCEND -> sortedBy { it.product.title }
            SortType.ALPHABETICAL_DESCEND -> sortedByDescending { it.product.title }
            SortType.PRICE_ASCEND -> sortedBy { it.product.price.value }
            SortType.PRICE_DESCEND -> sortedByDescending { it.product.price.value }
        }
    }
}
