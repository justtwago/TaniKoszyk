package com.github.justtwago.tanikoszyk.ui.home.list.paging.auchan

import androidx.paging.DataSource
import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.base.BaseProductDataSourceFactory
import com.github.justtwago.usecases.model.market.common.AUCHAN_PAGE_SIZE
import com.github.justtwago.usecases.usecases.market.GetAuchanProductPageUseCase


class AuchanProductDataSourceFactory(
        private val getAuchanProductPageUseCase: GetAuchanProductPageUseCase
) : BaseProductDataSourceFactory(AUCHAN_PAGE_SIZE) {

    override fun create(): DataSource<Int, ProductItemViewModel> {
        return AuchanProductDataSource(getAuchanProductPageUseCase, query, sortType, loadingLiveData, isReset, isNextPageLoaderVisibleLiveData, onProductClickListener).apply {
            dataSource = this
        }
    }
}