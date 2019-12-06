package com.tanikoszyk.ui.home.list.paging.kaufland

import androidx.lifecycle.MutableLiveData
import com.tanikoszyk.common.MarketsLoadingStatus
import com.tanikoszyk.common.extensions.postKauflandReady
import com.tanikoszyk.ui.base.BaseProductDataSource
import com.tanikoszyk.usecases.model.Result
import com.tanikoszyk.usecases.model.market.MarketPageRequest
import com.tanikoszyk.usecases.model.market.common.ProductPage
import com.tanikoszyk.usecases.model.market.common.SortType
import com.tanikoszyk.usecases.usecases.market.GetKauflandProductPageUseCase

class KauflandProductDataSource(
    private val getKauflandProductPageUseCase: GetKauflandProductPageUseCase,
    private val query: String,
    private val sortType: SortType,
    private val loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
    isReset: Boolean,
    isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : BaseProductDataSource(query, isReset, isNextPageLoaderVisibleLiveData) {

    override suspend fun loadProductPage(page: Int): ProductPage? {
        val result = getKauflandProductPageUseCase.execute(MarketPageRequest(query, page, sortType))
        return when (result) {
            is Result.Success -> result.result
            else -> null
        }
    }

    override fun onFirstProductPageLoaded(isLoaded: Boolean) {
        loadingLiveData.postKauflandReady(isLoaded)
    }
}