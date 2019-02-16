package com.github.justtwago.tanikoszyk.ui.home.list.paging.auchan

import androidx.lifecycle.MutableLiveData
import com.github.justtwago.service.common.Response
import com.github.justtwago.service.model.domain.ProductPage
import com.github.justtwago.service.model.domain.SortType
import com.github.justtwago.service.model.domain.mapToDomain
import com.github.justtwago.service.repositories.AuchanRepository
import com.github.justtwago.tanikoszyk.common.MarketsLoadingStatus
import com.github.justtwago.tanikoszyk.common.extensions.postAuchanReady
import com.github.justtwago.tanikoszyk.ui.base.BaseProductDataSource


class AuchanProductDataSource(
        private val repository: AuchanRepository,
        private val query: String,
        private val sortType: SortType,
        private val loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
        isReset: Boolean,
        isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : BaseProductDataSource(query, isReset, isNextPageLoaderVisibleLiveData) {

    override suspend fun loadProductPage(page: Int): ProductPage? {
        val response = repository.getProducts(query, page, sortType)
        return when (response) {
            is Response.Success.WithBody -> response.body.mapToDomain()
            else -> null
        }
    }

    override fun onFirstProductPageLoaded(isLoaded: Boolean) {
        loadingLiveData.postAuchanReady(isLoaded)
    }
}