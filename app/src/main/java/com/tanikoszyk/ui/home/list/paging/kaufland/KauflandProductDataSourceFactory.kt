package com.tanikoszyk.ui.home.list.paging.kaufland

import androidx.paging.DataSource
import com.fanmountain.domain.Market
import com.fanmountain.domain.MarketProduct
import com.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.tanikoszyk.usecases.usecases.market.GetKauflandProductPageUseCase

class KauflandProductDataSourceFactory(
    private val getKauflandProductPageUseCase: GetKauflandProductPageUseCase
) : BaseProductDataSourceFactory(Market.KAUFLAND.pageSize) {

    override fun create(): DataSource<Int, MarketProduct> {
        return KauflandProductDataSource(
            getKauflandProductPageUseCase,
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