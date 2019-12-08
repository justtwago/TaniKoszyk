package com.tanikoszyk.ui.home.list.paging.kaufland

import androidx.lifecycle.MutableLiveData
import com.tanikoszyk.common.MarketsLoadingStatus
import com.tanikoszyk.common.extensions.postKauflandReady
import com.tanikoszyk.domain.ProductPage
import com.tanikoszyk.domain.Result
import com.tanikoszyk.domain.SortType
import com.tanikoszyk.service.repositories.KauflandRepository
import com.tanikoszyk.ui.base.BaseProductDataSource

class KauflandProductDataSource(
    private val kauflandService: KauflandRepository,
    private val query: String,
    private val sortType: SortType,
    private val loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
    isReset: Boolean,
    isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : BaseProductDataSource(query, isReset, isNextPageLoaderVisibleLiveData) {

    override suspend fun loadProductPage(page: Int): ProductPage? {
        val result = kauflandService.getProducts(query, page, sortType)
        return (result as? Result.Success.WithBody)?.body
    }

    override fun onFirstProductPageLoaded(isLoaded: Boolean) {
        loadingLiveData.postKauflandReady(isLoaded)
    }
}