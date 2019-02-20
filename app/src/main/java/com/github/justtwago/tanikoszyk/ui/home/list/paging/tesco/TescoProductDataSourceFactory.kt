package com.github.justtwago.tanikoszyk.ui.home.list.paging.tesco

import androidx.paging.DataSource
import com.github.justtwago.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.github.justtwago.usecases.model.market.common.TESCO_PAGE_SIZE
import com.github.justtwago.usecases.usecases.market.GetTescoProductPageUseCase
import com.github.justtwago.usecases.usecases.realtimedb.CheckIfProductExistsUseCase

class TescoProductDataSourceFactory(
    private val getTescoProductPageUseCase: GetTescoProductPageUseCase,
    private val checkIfProductExistsUseCase: CheckIfProductExistsUseCase
) : BaseProductDataSourceFactory(TESCO_PAGE_SIZE) {

    override fun create(): DataSource<Int, SearchProductItemViewModel> {
        return TescoProductDataSource(
            getTescoProductPageUseCase,
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