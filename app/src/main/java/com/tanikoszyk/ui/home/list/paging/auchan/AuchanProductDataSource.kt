package com.tanikoszyk.ui.home.list.paging.auchan

import androidx.lifecycle.MutableLiveData
import com.tanikoszyk.common.MarketsLoadingStatus
import com.tanikoszyk.common.extensions.postAuchanReady
import com.tanikoszyk.domain.ProductPage
import com.tanikoszyk.domain.Result
import com.tanikoszyk.domain.SortType
import com.tanikoszyk.service.repositories.AuchanRepository
import com.tanikoszyk.ui.base.BaseProductDataSource

class AuchanProductDataSource(
    private val auchanRepository: AuchanRepository,
    private val query: String,
    private val sortType: SortType,
    private val loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
    isReset: Boolean,
    isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : BaseProductDataSource(query, isReset, isNextPageLoaderVisibleLiveData) {

    override suspend fun loadProductPage(page: Int): ProductPage? {
        val result = auchanRepository.getProducts(query, page, sortType)
        return (result as? Result.Success.WithBody)?.body
    }

    override fun onFirstProductPageLoaded(isLoaded: Boolean) {
        loadingLiveData.postAuchanReady(isLoaded)
    }
}