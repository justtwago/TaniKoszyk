package com.github.justtwago.usecases.usecases.market

import com.github.justtwago.service.common.Response
import com.github.justtwago.service.repositories.KauflandRepository
import com.github.justtwago.usecases.model.Result
import com.github.justtwago.usecases.model.market.MarketPageRequest
import com.github.justtwago.usecases.model.market.common.ProductPage
import com.github.justtwago.usecases.model.market.common.SortType
import com.github.justtwago.usecases.model.market.common.mapToDomain
import com.github.justtwago.usecases.usecases.base.AsyncUseCase


class GetKauflandProductPageUseCase(private val kauflandRepository: KauflandRepository) : AsyncUseCase<MarketPageRequest, Result<ProductPage>> {
    override suspend fun execute(request: MarketPageRequest): Result<ProductPage> {
        val response = kauflandRepository.getProducts(request.searchQuery, request.page)
        return when (response) {
            is Response.Success.WithBody -> {
                val productPage = response.body.mapToDomain()
                //TODO: Workaround because there is no way to sort products in website. API needed!
                val sortedProducts = when (request.sortType) {
                    SortType.TARGET -> productPage.products
                    SortType.ALPHABETICAL_ASCEND -> productPage.products.sortedBy { it.title }
                    SortType.ALPHABETICAL_DESCEND -> productPage.products.sortedByDescending { it.title }
                    SortType.PRICE_ASCEND -> productPage.products.sortedBy { it.price.substringBefore(" ").replace(',', '.').toDouble() }
                    SortType.PRICE_DESCEND -> productPage.products.sortedByDescending { it.price.substringBefore(" ").replace(',', '.').toDouble() }
                }
                Result.Success(productPage.copy(products = sortedProducts))
            }
            else -> Result.Failure()
        }
    }
}