package com.tanikoszyk.usecases.usecases.market

import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.repositories.BiedronkaRepository
import com.tanikoszyk.usecases.model.Result
import com.tanikoszyk.usecases.model.market.MarketPageRequest
import com.tanikoszyk.usecases.model.market.common.*
import com.tanikoszyk.usecases.usecases.base.AsyncUseCase


class GetBiedronkaProductPageUseCase(private val biedronkaRepository: BiedronkaRepository) : AsyncUseCase<MarketPageRequest, Result<ProductPage>> {
    override suspend fun execute(request: MarketPageRequest): Result<ProductPage> {
        val response = biedronkaRepository.getProducts(request.searchQuery, request.page)
        return when (response) {
            is Response.Success.WithBody -> {
                val productPage = getProductPage(response.body.mapToDomain(), request.page)
                //TODO: Workaround because there is no way to sort products in website. API needed!
                val sortedProducts = when (request.sortType) {
                    SortType.TARGET -> productPage?.products
                    SortType.ALPHABETICAL_ASCEND -> productPage?.products?.sortedBy { it.title }
                    SortType.ALPHABETICAL_DESCEND -> productPage?.products?.sortedByDescending { it.title }
                    SortType.PRICE_ASCEND -> productPage?.products?.sortedBy { it.price.substringBefore(" ").replace(',', '.').toDouble() }
                    SortType.PRICE_DESCEND -> productPage?.products?.sortedByDescending { it.price.substringBefore(" ").replace(',', '.').toDouble() }
                }
                productPage?.let { Result.Success(it.copy(products = sortedProducts.orEmpty())) } ?: Result.Failure()
            }
            else -> Result.Failure()
        }
    }

    private suspend fun getProductPage(productLinks: ProductIdPage, page: Int): ProductPage? {
        return if (productLinks.pageCount >= page) {
            ProductPage(
                products = getProductsFromLinks(productLinks),
                pageCount = productLinks.pageCount
            )
        } else null
    }

    private suspend fun getProductsFromLinks(productLinks: ProductIdPage): List<Product> {
        return productLinks.productIdList.mapNotNull {
            val productResponse = biedronkaRepository.getProduct(it)
            (productResponse as? Response.Success.WithBody)?.body?.mapToDomain()
        }.filter { it.isNotEmpty() }
    }
}