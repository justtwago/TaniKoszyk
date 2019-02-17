package com.github.justtwago.tanikoszyk.ui.home.list.paging.biedronka

import androidx.paging.DataSource
import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.github.justtwago.usecases.model.market.common.BIEDRONKA_PAGE_SIZE
import com.github.justtwago.usecases.usecases.market.GetBiedronkaProductPageUseCase


class BiedronkaProductDataSourceFactory(
        private val getBiedronkaProductPageUseCase: GetBiedronkaProductPageUseCase
) : BaseProductDataSourceFactory(BIEDRONKA_PAGE_SIZE) {

    override fun create(): DataSource<Int, ProductItemViewModel> {
        return BiedronkaProductDataSource(getBiedronkaProductPageUseCase, query, sortType, loadingLiveData, isReset, isNextPageLoaderVisibleLiveData).apply {
            dataSource = this
        }
    }
}