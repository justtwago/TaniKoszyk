package com.tanikoszyk.ui.home.list.paging.biedronka

import androidx.paging.DataSource
import com.tanikoszyk.domain.Market
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.service.repositories.BiedronkaRepository
import com.tanikoszyk.ui.base.BaseProductDataSourceFactory
import javax.inject.Inject

class BiedronkaProductDataSourceFactory @Inject constructor(
    private val biedronkaRepository: BiedronkaRepository
) : BaseProductDataSourceFactory(Market.BIEDRONKA.pageSize) {

    override fun create(): DataSource<Int, MarketProduct> {
        return BiedronkaProductDataSource(
            biedronkaRepository,
            query,
            sortType,
            marketsLoadingStatusLiveData,
            isReset,
            isPageLoadingLiveData
        ).apply {
            dataSource = this
        }
    }
}