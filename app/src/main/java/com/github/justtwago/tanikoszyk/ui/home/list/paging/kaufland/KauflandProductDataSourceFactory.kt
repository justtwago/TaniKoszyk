package com.github.justtwago.tanikoszyk.ui.home.list.paging.kaufland

import androidx.paging.DataSource
import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.github.justtwago.usecases.model.market.common.KAUFLAND_PAGE_SIZE
import com.github.justtwago.usecases.usecases.market.GetKauflandProductPageUseCase


class KauflandProductDataSourceFactory(
        private val getKauflandProductPageUseCase: GetKauflandProductPageUseCase
) : BaseProductDataSourceFactory(KAUFLAND_PAGE_SIZE) {

    override fun create(): DataSource<Int, ProductItemViewModel> {
        return KauflandProductDataSource(getKauflandProductPageUseCase, query, sortType, loadingLiveData, isReset, isNextPageLoaderVisibleLiveData).apply {
            dataSource = this
        }
    }
}