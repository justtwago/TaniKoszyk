package com.github.justtwago.tanikoszyk.ui.home.list.paging.biedronka

import androidx.paging.DataSource
import com.github.justtwago.service.repositories.BIEDRONKA_PAGE_SIZE
import com.github.justtwago.service.repositories.BiedronkaRepository
import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.home.list.paging.base.BaseProductDataSourceFactory


class BiedronkaProductDataSourceFactory(
        private val repository: BiedronkaRepository
) : BaseProductDataSourceFactory(BIEDRONKA_PAGE_SIZE) {

    override fun create(): DataSource<Int, ProductItemViewModel> {
        return BiedronkaProductDataSource(repository, query, sortType, loadingLiveData, isReset, isNextPageLoaderVisibleLiveData).apply {
            dataSource = this
        }
    }
}