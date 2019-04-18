package com.tanikoszyk.ui.home.list.paging.tesco

import androidx.paging.DataSource
import com.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.tanikoszyk.usecases.model.market.common.TESCO_PAGE_SIZE
import com.tanikoszyk.usecases.usecases.market.GetTescoProductPageUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.CheckIfProductExistsUseCase

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