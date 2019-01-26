package com.github.justtwago.tanikoszyk.ui.home.list.paging.kaufland

import androidx.lifecycle.MutableLiveData
import com.github.justtwago.service.common.Response
import com.github.justtwago.service.model.domain.ProductPage
import com.github.justtwago.service.model.domain.SortType
import com.github.justtwago.service.model.domain.mapToDomain
import com.github.justtwago.service.repositories.KauflandRepository
import com.github.justtwago.tanikoszyk.common.MarketsLoadingStatus
import com.github.justtwago.tanikoszyk.common.extensions.postKauflandReady
import com.github.justtwago.tanikoszyk.ui.home.list.paging.base.BaseProductDataSource

class KauflandProductDataSource(
        private val repository: KauflandRepository,
        private val query: String,
        private val sortType: SortType,
        private val loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
        isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : BaseProductDataSource(query, isNextPageLoaderVisibleLiveData) {

    override suspend fun loadProductPage(page: Int): ProductPage? {
        val response = repository.getProducts(query, page)
        return when (response) {
            is Response.Success.WithBody -> {
                val productPage = response.body.mapToDomain()
                //TODO: Workaround because there is no way to sort products in website. API needed!
                val sortedProducts = when (sortType) {
                    SortType.TARGET -> productPage.products
                    SortType.ALPHABETICAL_ASCEND -> productPage.products.sortedBy { it.title }
                    SortType.ALPHABETICAL_DESCEND -> productPage.products.sortedByDescending { it.title }
                    SortType.PRICE_ASCEND -> productPage.products.sortedBy { it.price.substringBefore(" ").replace(',', '.').toDouble() }
                    SortType.PRICE_DESCEND -> productPage.products.sortedByDescending { it.price.substringBefore(" ").replace(',', '.').toDouble() }
                }
                productPage.copy(products = sortedProducts)
            }
            else -> null
        }
    }

    override fun allContentProductsReady(isReady: Boolean) {
        loadingLiveData.postKauflandReady(isReady)
    }
}