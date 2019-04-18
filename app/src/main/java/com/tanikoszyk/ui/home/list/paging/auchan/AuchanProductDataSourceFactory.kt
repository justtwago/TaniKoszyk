package com.tanikoszyk.ui.home.list.paging.auchan

import androidx.paging.DataSource
import com.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.tanikoszyk.usecases.model.market.common.AUCHAN_PAGE_SIZE
import com.tanikoszyk.usecases.usecases.market.GetAuchanProductPageUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.CheckIfProductExistsUseCase

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