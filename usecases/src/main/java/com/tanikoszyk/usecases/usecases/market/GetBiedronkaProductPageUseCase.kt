package com.tanikoszyk.usecases.usecases.market

import com.fanmountain.domain.MarketProduct
import com.fanmountain.domain.ProductPage
import com.fanmountain.domain.SortType
import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.model.data.ProductIdPage
import com.tanikoszyk.service.model.mappers.mapToDomain
import com.tanikoszyk.service.repositories.BiedronkaRepository
import com.tanikoszyk.usecases.requests.MarketPageRequest
import com.tanikoszyk.usecases.requests.Result
import com.tanikoszyk.usecases.usecases.base.AsyncUseCase

class GetBiedronkaProductPageUseCase(
    private val biedronkaRepository: BiedronkaRepository
) :
    AsyncUseCase<MarketPageRequest, Result<ProductPage>> {

    override suspend fun execute(request: MarketPageRequest): Result<ProductPage> {
        val response = biedronkaRepository.getProducts(request.searchQuery, request.page)
        return when (response) {
            is Response.Success.WithBody -> {
                val productPage = getProductPage(response.body.mapToDomain(), request.page)
                //TODO: Workaround because there is no way to sort products in website. API needed!
                val sortedProducts = when (request.sortType) {
                    SortType.TARGET -> productPage?.marketProducts
                    SortType.ALPHABETICAL_ASCEND -> productPage?.marketProducts?.sortedBy { it.product.title }
                    SortType.ALPHABETICAL_DESCEND -> productPage?.marketProducts?.sortedByDescending { it.product.title }
                    SortType.PRICE_ASCEND -> productPage?.marketProducts?.sortedBy {
                        it.product.price.substringBefore(" ").replace(',', '.').toDouble()
                    }
                    SortType.PRICE_DESCEND -> productPage?.marketProducts?.sortedByDescending {
                        it.product.price.substringBefore(" ").replace(',', '.').toDouble()
                    }
                }.orEmpty()
                productPage?.let { Result.Success(it.copy(marketProducts = sortedProducts)) } ?: Result.Failure()
            }
            else -> Result.Failure()
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
            val productResponse = biedronkaRepository.getProduct(it)
            (productResponse as? Response.Success.WithBody)?.body?.mapToDomain(url = it)
        }
    }
}