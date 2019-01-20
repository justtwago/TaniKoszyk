package com.github.justtwago.tanikoszyk.ui.search.list.paging.biedronka

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.github.justtwago.service.repositories.BIEDRONKA_PAGE_SIZE
import com.github.justtwago.service.repositories.BiedronkaRepository
import com.github.justtwago.tanikoszyk.ui.search.list.ProductItemViewModel


class BiedronkaProductDataSourceFactory(
        private val repository: BiedronkaRepository
) : DataSource.Factory<Int, ProductItemViewModel>() {

    private lateinit var isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
    private lateinit var query: String
    var dataSource: PageKeyedDataSource<Int, ProductItemViewModel>? = null
        private set

    fun initialize(
            query: String,
            isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
    ): LiveData<PagedList<ProductItemViewModel>> {
        this.query = query
        this.isNextPageLoaderVisibleLiveData = isNextPageLoaderVisibleLiveData

        val config = PagedList.Config.Builder()
            .setPageSize(BIEDRONKA_PAGE_SIZE)
            .setPrefetchDistance(BIEDRONKA_PAGE_SIZE - 5)
            .build()
        return LivePagedListBuilder<Int, ProductItemViewModel>(
            this,
            config
        ).build()
    }

    fun invalidate(query: String, isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>) {
        initialize(query, isNextPageLoaderVisibleLiveData)
        dataSource?.invalidate()
    }

    override fun create(): DataSource<Int, ProductItemViewModel> {
        return BiedronkaProductDataSource(repository, query, isNextPageLoaderVisibleLiveData).apply {
            dataSource = this
        }
    }
}