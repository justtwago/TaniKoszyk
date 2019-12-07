package com.tanikoszyk.usecases.usecases.market

import com.fanmountain.domain.ProductPage
import com.fanmountain.domain.SortType
import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.model.mappers.mapToDomain
import com.tanikoszyk.service.repositories.KauflandRepository
import com.tanikoszyk.usecases.requests.MarketPageRequest
import com.tanikoszyk.usecases.requests.Result
import com.tanikoszyk.usecases.usecases.base.AsyncUseCase

class GetKauflandProductPageUseCase(
    private val kauflandRepository: KauflandRepository
) :
    AsyncUseCase<MarketPageRequest, Result<ProductPage>> {

    override suspend fun execute(request: MarketPageRequest): Result<ProductPage> {
        val response = kauflandRepository.getProducts(request.searchQuery, request.page)
        return when (response) {
            is Response.Success.WithBody -> {
                val productPage = response.body.mapToDomain()
                //TODO: Workaround because there is no way to sort products in website. API needed!
                val sortedProducts = when (request.sortType) {
                    SortType.TARGET -> productPage.marketProducts
                    SortType.ALPHABETICAL_ASCEND -> productPage.marketProducts.sortedBy { it.product.title }
                    SortType.ALPHABETICAL_DESCEND -> productPage.marketProducts.sortedByDescending { it.product.title }
                    SortType.PRICE_ASCEND -> productPage.marketProducts.sortedBy {
                        it.product.price.substringBefore(" ").replace(',', '.').toDouble()
                    }
                    SortType.PRICE_DESCEND -> productPage.marketProducts.sortedByDescending {
                        it.product.price.substringBefore(" ").replace(',', '.').toDouble()
                    }
                }
                Result.Success(productPage.copy(marketProducts = sortedProducts))
            }
            else -> Result.Failure()
        }
    }
}