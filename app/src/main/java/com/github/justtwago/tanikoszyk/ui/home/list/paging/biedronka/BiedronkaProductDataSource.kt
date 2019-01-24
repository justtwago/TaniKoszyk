package com.github.justtwago.tanikoszyk.ui.home.list.paging.biedronka

import androidx.lifecycle.MutableLiveData
import com.github.justtwago.service.common.Response
import com.github.justtwago.service.model.domain.Product
import com.github.justtwago.service.model.domain.ProductIdPage
import com.github.justtwago.service.model.domain.ProductPage
import com.github.justtwago.service.model.domain.mapToDomain
import com.github.justtwago.service.repositories.BiedronkaRepository
import com.github.justtwago.tanikoszyk.common.MarketsLoadingStatus
import com.github.justtwago.tanikoszyk.common.extensions.postBiedronkaReady
import com.github.justtwago.tanikoszyk.ui.home.list.paging.base.BaseProductDataSource


class BiedronkaProductDataSource(
        private val repository: BiedronkaRepository,
        private val query: String,
        private val loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
        isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : BaseProductDataSource(query, isNextPageLoaderVisibleLiveData) {

    override suspend fun loadProductPage(page: Int): ProductPage? {
        val response = repository.getProducts(query, page)
        return when (response) {
            is Response.Success.WithBody -> getProductPage(response.body.mapToDomain(), page)
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