package com.github.justtwago.tanikoszyk.ui.home.list.paging.kaufland

import androidx.paging.DataSource
import com.github.justtwago.service.repositories.KAUFLAND_PAGE_SIZE
import com.github.justtwago.service.repositories.KauflandRepository
import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.base.BaseProductDataSourceFactory


class KauflandProductDataSourceFactory(
        private val repository: KauflandRepository
) : BaseProductDataSourceFactory(KAUFLAND_PAGE_SIZE) {

    override fun create(): DataSource<Int, ProductItemViewModel> {
        return KauflandProductDataSource(repository, query, sortType, loadingLiveData, isReset, isNextPageLoaderVisibleLiveData).apply {
            dataSource = this
        }
    }
}