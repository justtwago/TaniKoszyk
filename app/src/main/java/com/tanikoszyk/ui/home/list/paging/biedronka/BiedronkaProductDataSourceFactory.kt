package com.tanikoszyk.ui.home.list.paging.biedronka

import androidx.paging.DataSource
import com.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.tanikoszyk.usecases.model.market.common.BIEDRONKA_PAGE_SIZE
import com.tanikoszyk.usecases.usecases.market.GetBiedronkaProductPageUseCase

class BiedronkaProductDataSourceFactory(
    private val getBiedronkaProductPageUseCase: GetBiedronkaProductPageUseCase
) : BaseProductDataSourceFactory(BIEDRONKA_PAGE_SIZE) {

    override fun create(): DataSource<Int, SearchProductItemViewModel> {
        return BiedronkaProductDataSource(
            getBiedronkaProductPageUseCase,
            query,
            sortType,
            loadingLiveData,
            isReset,
            isNextPageLoaderVisibleLiveData
        ).apply {
            dataSource = this
        }
    }
}