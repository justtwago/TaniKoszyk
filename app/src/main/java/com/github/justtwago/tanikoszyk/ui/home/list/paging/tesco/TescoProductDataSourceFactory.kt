package com.github.justtwago.tanikoszyk.ui.home.list.paging.tesco

import androidx.paging.DataSource
import com.github.justtwago.service.repositories.TESCO_PAGE_SIZE
import com.github.justtwago.service.repositories.TescoRepository
import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.home.list.paging.base.BaseProductDataSourceFactory


class TescoProductDataSourceFactory(
        private val repository: TescoRepository
) : BaseProductDataSourceFactory(TESCO_PAGE_SIZE) {

    override fun create(): DataSource<Int, ProductItemViewModel> {
        return TescoProductDataSource(repository, query, loadingLiveData, isNextPageLoaderVisibleLiveData).apply {
            dataSource = this
        }
    }
}