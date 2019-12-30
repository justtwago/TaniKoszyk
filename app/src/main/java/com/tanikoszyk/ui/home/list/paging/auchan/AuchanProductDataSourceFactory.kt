package com.tanikoszyk.ui.home.list.paging.auchan

import androidx.paging.DataSource
import com.tanikoszyk.domain.Market
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.service.repositories.AuchanRepository
import com.tanikoszyk.ui.base.BaseProductDataSourceFactory
import javax.inject.Inject

class AuchanProductDataSourceFactory @Inject constructor(
    private val auchanRepository: AuchanRepository
) : BaseProductDataSourceFactory(Market.AUCHAN.pageSize) {

    override fun create(): DataSource<Int, MarketProduct> {
        return AuchanProductDataSource(
            auchanRepository,
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