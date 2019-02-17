package com.github.justtwago.tanikoszyk.ui.home.list.paging.tesco

import androidx.paging.DataSource
import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.github.justtwago.usecases.model.market.common.TESCO_PAGE_SIZE
import com.github.justtwago.usecases.usecases.market.GetTescoProductPageUseCase


class TescoProductDataSourceFactory(
        private val getTescoProductPageUseCase: GetTescoProductPageUseCase
) : BaseProductDataSourceFactory(TESCO_PAGE_SIZE) {

    override fun create(): DataSource<Int, ProductItemViewModel> {
        return TescoProductDataSource(getTescoProductPageUseCase, query, sortType, loadingLiveData, isReset, isNextPageLoaderVisibleLiveData, onProductClickListener).apply {
            dataSource = this
        }
    }
}