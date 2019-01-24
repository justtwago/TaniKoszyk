package com.github.justtwago.tanikoszyk.ui.home.list.paging.kaufland

import androidx.lifecycle.MutableLiveData
import com.github.justtwago.service.common.Response
import com.github.justtwago.service.model.domain.ProductPage
import com.github.justtwago.service.model.domain.mapToDomain
import com.github.justtwago.service.repositories.KauflandRepository
import com.github.justtwago.tanikoszyk.common.MarketsLoadingStatus
import com.github.justtwago.tanikoszyk.common.extensions.postKauflandReady
import com.github.justtwago.tanikoszyk.ui.home.list.paging.base.BaseProductDataSource

class KauflandProductDataSource(
        private val repository: KauflandRepository,
        private val query: String,
        private val loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
        isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : BaseProductDataSource(query, isNextPageLoaderVisibleLiveData) {

    override suspend fun loadProductPage(page: Int): ProductPage? {
        val response = repository.getProducts(query, page)
        return when (response) {
            is Response.Success.WithBody -> response.body.mapToDomain()
            else -> null
        }
    }

    override fun allContentProductsReady(isReady: Boolean) {
        loadingLiveData.postKauflandReady(isReady)
    }
}