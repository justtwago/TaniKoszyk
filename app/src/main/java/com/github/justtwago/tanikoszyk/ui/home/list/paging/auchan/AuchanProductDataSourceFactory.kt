package com.github.justtwago.tanikoszyk.ui.home.list.paging.auchan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.github.justtwago.service.repositories.AUCHAN_PAGE_SIZE
import com.github.justtwago.service.repositories.AuchanRepository
import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel


class AuchanProductDataSourceFactory(
        private val repository: AuchanRepository
) : DataSource.Factory<Int, ProductItemViewModel>() {

    private lateinit var isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
    private lateinit var query: String
    private var dataSource: PageKeyedDataSource<Int, ProductItemViewModel>? = null

    fun initialize(
            query: String,
            isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
    ): LiveData<PagedList<ProductItemViewModel>> {
        this.query = query
        this.isNextPageLoaderVisibleLiveData = isNextPageLoaderVisibleLiveData

        val config = PagedList.Config.Builder()
            .setPageSize(AUCHAN_PAGE_SIZE)
            .setPrefetchDistance(AUCHAN_PAGE_SIZE - 5)
            .build()
        return LivePagedListBuilder<Int, ProductItemViewModel>(this, config).build()
    }

    fun invalidate(query: String, isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>) {
        initialize(query, isNextPageLoaderVisibleLiveData)
        dataSource?.invalidate()
    }

    override fun create(): DataSource<Int, ProductItemViewModel> {
        return AuchanProductDataSource(repository, query, isNextPageLoaderVisibleLiveData).apply {
            dataSource = this
        }
    }
}