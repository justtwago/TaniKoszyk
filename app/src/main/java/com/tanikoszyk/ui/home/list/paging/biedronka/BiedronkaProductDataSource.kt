package com.tanikoszyk.ui.home.list.paging.biedronka

import androidx.lifecycle.MutableLiveData
import com.fanmountain.domain.ProductPage
import com.fanmountain.domain.SortType
import com.tanikoszyk.common.MarketsLoadingStatus
import com.tanikoszyk.common.extensions.postBiedronkaReady
import com.tanikoszyk.ui.base.BaseProductDataSource
import com.tanikoszyk.usecases.requests.MarketPageRequest
import com.tanikoszyk.usecases.requests.Result
import com.tanikoszyk.usecases.usecases.market.GetBiedronkaProductPageUseCase

class BiedronkaProductDataSource(
    private val getBiedronkaProductPageUseCase: GetBiedronkaProductPageUseCase,
    private val query: String,
    private val sortType: SortType,
    private val loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
    isReset: Boolean,
    isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : BaseProductDataSource(query, isReset, isNextPageLoaderVisibleLiveData) {

    override suspend fun loadProductPage(page: Int): ProductPage? {
        val result = getBiedronkaProductPageUseCase.execute(
            MarketPageRequest(
                query,
                page,
                sortType
            )
        )
        return when (result) {
            is Result.Success -> result.result
            else -> null
        }
    }

    override fun onFirstProductPageLoaded(isLoaded: Boolean) {
        loadingLiveData.postBiedronkaReady(isLoaded)
    }
}