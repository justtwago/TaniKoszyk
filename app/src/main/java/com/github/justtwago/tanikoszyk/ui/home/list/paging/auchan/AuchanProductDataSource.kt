package com.github.justtwago.tanikoszyk.ui.home.list.paging.auchan

import androidx.lifecycle.MutableLiveData
import com.github.justtwago.tanikoszyk.common.MarketsLoadingStatus
import com.github.justtwago.tanikoszyk.common.extensions.postAuchanReady
import com.github.justtwago.tanikoszyk.ui.base.BaseProductDataSource
import com.github.justtwago.usecases.model.Result
import com.github.justtwago.usecases.model.market.MarketPageRequest
import com.github.justtwago.usecases.model.market.common.ProductPage
import com.github.justtwago.usecases.model.market.common.SortType
import com.github.justtwago.usecases.usecases.market.GetAuchanProductPageUseCase


class AuchanProductDataSource(
        private val getAuchanProductPageUseCase: GetAuchanProductPageUseCase,
        private val query: String,
        private val sortType: SortType,
        private val loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
        isReset: Boolean,
        isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : BaseProductDataSource(query, isReset, isNextPageLoaderVisibleLiveData) {

    override suspend fun loadProductPage(page: Int): ProductPage? {
        val result = getAuchanProductPageUseCase.execute(MarketPageRequest(query, page, sortType))
        return when (result) {
            is Result.Success -> result.result
            else -> null
        }
    }

    override fun onFirstProductPageLoaded(isLoaded: Boolean) {
        loadingLiveData.postAuchanReady(isLoaded)
    }
}