package com.github.justtwago.tanikoszyk.ui.home.list.paging.biedronka

import androidx.lifecycle.MutableLiveData
import com.github.justtwago.tanikoszyk.common.MarketsLoadingStatus
import com.github.justtwago.tanikoszyk.common.extensions.postBiedronkaReady
import com.github.justtwago.tanikoszyk.ui.base.BaseProductDataSource
import com.github.justtwago.usecases.model.Result
import com.github.justtwago.usecases.model.market.MarketPageRequest
import com.github.justtwago.usecases.model.market.common.Product
import com.github.justtwago.usecases.model.market.common.ProductPage
import com.github.justtwago.usecases.model.market.common.SortType
import com.github.justtwago.usecases.usecases.market.GetBiedronkaProductPageUseCase
import com.github.justtwago.usecases.usecases.realtimedb.CheckIfProductExistsUseCase

class BiedronkaProductDataSource(
    private val getBiedronkaProductPageUseCase: GetBiedronkaProductPageUseCase,
    private val query: String,
    private val sortType: SortType,
    private val loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
    checkIfProductExistsUseCase: CheckIfProductExistsUseCase,
    isReset: Boolean,
    isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>,
    onProductClickListener: (Product) -> Boolean
) : BaseProductDataSource(query, isReset, isNextPageLoaderVisibleLiveData, checkIfProductExistsUseCase, onProductClickListener) {

    override suspend fun loadProductPage(page: Int): ProductPage? {
        val result = getBiedronkaProductPageUseCase.execute(MarketPageRequest(query, page, sortType))
        return when (result) {
            is Result.Success -> result.result
            else -> null
        }
    }

    override fun onFirstProductPageLoaded(isLoaded: Boolean) {
        loadingLiveData.postBiedronkaReady(isLoaded)
    }
}