package com.github.justtwago.tanikoszyk.ui.home.list.paging.biedronka

import androidx.paging.DataSource
import com.github.justtwago.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.github.justtwago.usecases.model.market.common.BIEDRONKA_PAGE_SIZE
import com.github.justtwago.usecases.usecases.market.GetBiedronkaProductPageUseCase
import com.github.justtwago.usecases.usecases.realtimedb.CheckIfProductExistsUseCase

class BiedronkaProductDataSourceFactory(
    private val getBiedronkaProductPageUseCase: GetBiedronkaProductPageUseCase,
    private val checkIfProductExistsUseCase: CheckIfProductExistsUseCase
) : BaseProductDataSourceFactory(BIEDRONKA_PAGE_SIZE) {

    override fun create(): DataSource<Int, SearchProductItemViewModel> {
        return BiedronkaProductDataSource(
            getBiedronkaProductPageUseCase,
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