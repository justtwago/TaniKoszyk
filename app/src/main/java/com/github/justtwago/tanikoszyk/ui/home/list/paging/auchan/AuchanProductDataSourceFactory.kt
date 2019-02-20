package com.github.justtwago.tanikoszyk.ui.home.list.paging.auchan

import androidx.paging.DataSource
import com.github.justtwago.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.github.justtwago.usecases.model.market.common.AUCHAN_PAGE_SIZE
import com.github.justtwago.usecases.usecases.market.GetAuchanProductPageUseCase
import com.github.justtwago.usecases.usecases.realtimedb.CheckIfProductExistsUseCase

class AuchanProductDataSourceFactory(
    private val getAuchanProductPageUseCase: GetAuchanProductPageUseCase,
    private val checkIfProductExistsUseCase: CheckIfProductExistsUseCase
) : BaseProductDataSourceFactory(AUCHAN_PAGE_SIZE) {

    override fun create(): DataSource<Int, SearchProductItemViewModel> {
        return AuchanProductDataSource(
            getAuchanProductPageUseCase,
            query,
            sortType,
            loadingLiveData,
            checkIfProductExistsUseCase,
            isReset,
            isNextPageLoaderVisibleLiveData,
            onProductClickListener
        ).apply {
            dataSource = this
        }
    }
}