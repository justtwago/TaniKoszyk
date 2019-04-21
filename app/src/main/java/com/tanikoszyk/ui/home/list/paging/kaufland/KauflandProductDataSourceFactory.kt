package com.tanikoszyk.ui.home.list.paging.kaufland

import androidx.paging.DataSource
import com.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.tanikoszyk.usecases.model.market.common.KAUFLAND_PAGE_SIZE
import com.tanikoszyk.usecases.model.market.common.Product
import com.tanikoszyk.usecases.usecases.market.GetKauflandProductPageUseCase

class KauflandProductDataSourceFactory(
    private val getKauflandProductPageUseCase: GetKauflandProductPageUseCase
) : BaseProductDataSourceFactory(KAUFLAND_PAGE_SIZE) {

    override fun create(): DataSource<Int, Product> {
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