package com.tanikoszyk.ui.home.list.paging.auchan

import androidx.paging.DataSource
import com.fanmountain.domain.Market
import com.fanmountain.domain.MarketProduct
import com.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.tanikoszyk.usecases.usecases.market.GetAuchanProductPageUseCase

class AuchanProductDataSourceFactory(
    private val getAuchanProductPageUseCase: GetAuchanProductPageUseCase
) : BaseProductDataSourceFactory(Market.AUCHAN.pageSize) {

    override fun create(): DataSource<Int, MarketProduct> {
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