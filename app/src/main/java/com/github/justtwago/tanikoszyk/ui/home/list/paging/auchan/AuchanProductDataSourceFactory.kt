package com.github.justtwago.tanikoszyk.ui.home.list.paging.auchan

import androidx.paging.DataSource
import com.github.justtwago.service.repositories.AUCHAN_PAGE_SIZE
import com.github.justtwago.service.repositories.AuchanRepository
import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.home.list.paging.base.BaseProductDataSourceFactory


class AuchanProductDataSourceFactory(
        private val repository: AuchanRepository
) : BaseProductDataSourceFactory(AUCHAN_PAGE_SIZE) {


    override fun create(): DataSource<Int, ProductItemViewModel> {
        return AuchanProductDataSource(repository, query, loadingLiveData, isNextPageLoaderVisibleLiveData).apply {
            dataSource = this
        }
    }
}