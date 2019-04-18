package com.tanikoszyk.ui.home.list.paging.kaufland

import androidx.paging.DataSource
import com.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.tanikoszyk.usecases.model.market.common.KAUFLAND_PAGE_SIZE
import com.tanikoszyk.usecases.usecases.market.GetKauflandProductPageUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.CheckIfProductExistsUseCase

class KauflandProductDataSourceFactory(
    private val getKauflandProductPageUseCase: GetKauflandProductPageUseCase,
    private val checkIfProductExistsUseCase: CheckIfProductExistsUseCase
) : BaseProductDataSourceFactory(KAUFLAND_PAGE_SIZE) {

    override fun create(): DataSource<Int, SearchProductItemViewModel> {
        return KauflandProductDataSource(
            getKauflandProductPageUseCase,
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