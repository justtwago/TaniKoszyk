package com.tanikoszyk.usecases.usecases.market

import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.repositories.KauflandRepository
import com.tanikoszyk.usecases.model.Result
import com.tanikoszyk.usecases.model.market.MarketPageRequest
import com.tanikoszyk.usecases.model.market.common.ProductPage
import com.tanikoszyk.usecases.model.market.common.SortType
import com.tanikoszyk.usecases.model.market.common.mapToDomain
import com.tanikoszyk.usecases.usecases.base.AsyncUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.CheckIfProductExistsUseCase

class GetKauflandProductPageUseCase(
    private val kauflandRepository: KauflandRepository,
    private val checkIfProductExistsUseCase: CheckIfProductExistsUseCase
) :
    AsyncUseCase<MarketPageRequest, Result<ProductPage>> {

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
                    SortType.PRICE_ASCEND -> productPage.products.sortedBy {
                        it.price.substringBefore(" ").replace(',', '.').toDouble()
                    }
                    SortType.PRICE_DESCEND -> productPage.products.sortedByDescending {
                        it.price.substringBefore(" ").replace(',', '.').toDouble()
                    }
                }.map {
                    it.copy(isSelected = checkIfProductExistsUseCase.execute(it))
                }
                Result.Success(productPage.copy(products = sortedProducts))
            }
            else -> Result.Failure()
        }
    }
}