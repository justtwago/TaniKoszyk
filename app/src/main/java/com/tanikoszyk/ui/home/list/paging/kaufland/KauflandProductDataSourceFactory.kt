package com.tanikoszyk.ui.home.list.paging.kaufland

import androidx.paging.DataSource
import com.tanikoszyk.domain.Market
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.service.repositories.KauflandRepository
import com.tanikoszyk.ui.base.BaseProductDataSourceFactory
import javax.inject.Inject

class KauflandProductDataSourceFactory @Inject constructor(
    private val kauflandService: KauflandRepository
) : BaseProductDataSourceFactory(Market.KAUFLAND.pageSize) {

    override fun create(): DataSource<Int, MarketProduct> {
        return KauflandProductDataSource(
            kauflandService,
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