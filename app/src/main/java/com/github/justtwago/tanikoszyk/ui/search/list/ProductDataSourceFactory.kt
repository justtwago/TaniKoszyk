package com.github.justtwago.tanikoszyk.ui.search.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.github.justtwago.tanikoszyk.services.base.MarketsRepository


class ProductDataSourceFactory(
        private val repository: MarketsRepository
) : DataSource.Factory<Int, SearchProductItemViewModel>() {

    private lateinit var isLoaderVisibleLiveData: MutableLiveData<Boolean>
    private lateinit var query: String
    var dataSource: PageKeyedDataSource<Int, SearchProductItemViewModel>? = null
        private set

    fun initialize(
            query: String,
            isLoaderVisibleLiveData: MutableLiveData<Boolean>
    ) {
        this.query = query
        this.isLoaderVisibleLiveData = isLoaderVisibleLiveData
    }

    override fun create(): DataSource<Int, SearchProductItemViewModel> {
        return ProductDataSource(repository, query, isLoaderVisibleLiveData).apply {
            dataSource = this
        }
    }
}