package com.tanikoszyk.ui.home.list.paging.auchan

import androidx.paging.DataSource
import com.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.tanikoszyk.usecases.model.market.common.AUCHAN_PAGE_SIZE
import com.tanikoszyk.usecases.model.market.common.Product
import com.tanikoszyk.usecases.usecases.market.GetAuchanProductPageUseCase

class AuchanProductDataSourceFactory(
    private val getAuchanProductPageUseCase: GetAuchanProductPageUseCase
) : BaseProductDataSourceFactory(AUCHAN_PAGE_SIZE) {

    override fun create(): DataSource<Int, Product> {
        return AuchanProductDataSource(
            getAuchanProductPageUseCase,
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