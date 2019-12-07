package com.tanikoszyk.ui.home.list.paging.biedronka

import androidx.paging.DataSource
import com.fanmountain.domain.Market
import com.fanmountain.domain.MarketProduct
import com.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.tanikoszyk.usecases.usecases.market.GetBiedronkaProductPageUseCase

class BiedronkaProductDataSourceFactory(
    private val getBiedronkaProductPageUseCase: GetBiedronkaProductPageUseCase
) : BaseProductDataSourceFactory(Market.BIEDRONKA.pageSize) {

    override fun create(): DataSource<Int, MarketProduct> {
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