package com.github.justtwago.tanikoszyk.ui.home.list.paging.biedronka

import androidx.lifecycle.MutableLiveData
import com.github.justtwago.service.common.Response
import com.github.justtwago.service.model.domain.*
import com.github.justtwago.service.repositories.BiedronkaRepository
import com.github.justtwago.tanikoszyk.common.MarketsLoadingStatus
import com.github.justtwago.tanikoszyk.common.extensions.postBiedronkaReady
import com.github.justtwago.tanikoszyk.ui.home.list.paging.base.BaseProductDataSource


class BiedronkaProductDataSource(
        private val repository: BiedronkaRepository,
        private val query: String,
        private val sortType: SortType,
        private val loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
        isReset: Boolean,
        isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : BaseProductDataSource(query, isReset, isNextPageLoaderVisibleLiveData) {

    override suspend fun loadProductPage(page: Int): ProductPage? {
        val response = repository.getProducts(query, page)
        return when (response) {
            is Response.Success.WithBody -> {
                val productPage = getProductPage(response.body.mapToDomain(), page)
                //TODO: Workaround because there is no way to sort products in website. API needed!
                val sortedProducts = when (sortType) {
                    SortType.TARGET -> productPage?.products
                    SortType.ALPHABETICAL_ASCEND -> productPage?.products?.sortedBy { it.title }
                    SortType.ALPHABETICAL_DESCEND -> productPage?.products?.sortedByDescending { it.title }
                    SortType.PRICE_ASCEND -> productPage?.products?.sortedBy { it.price.substringBefore(" ").replace(',', '.').toDouble() }
                    SortType.PRICE_DESCEND -> productPage?.products?.sortedByDescending { it.price.substringBefore(" ").replace(',', '.').toDouble() }
                }
                productPage?.copy(products = sortedProducts.orEmpty())
            }
            else -> null
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
            val productResponse = repository.getProduct(it)
            (productResponse as? Response.Success.WithBody)?.body?.mapToDomain()
        }.filter { it.isNotEmpty() }
    }

    override fun allContentProductsReady(isReady: Boolean) {
        loadingLiveData.postBiedronkaReady(isReady)
    }
}