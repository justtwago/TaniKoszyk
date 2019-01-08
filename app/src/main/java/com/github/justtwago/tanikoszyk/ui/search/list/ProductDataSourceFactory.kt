package com.github.justtwago.tanikoszyk.ui.search.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.github.justtwago.tanikoszyk.services.MarketsRepository


class ProductDataSourceFactory(
        private val repository: MarketsRepository
) : DataSource.Factory<Int, SearchProductItemViewModel>() {

    private lateinit var isInitialLoaderVisibleLiveData: MutableLiveData<Boolean>
    private lateinit var isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
    private lateinit var query: String
    var dataSource: PageKeyedDataSource<Int, SearchProductItemViewModel>? = null
        private set

    fun initialize(
            query: String,
            isInitialLoaderVisibleLiveData: MutableLiveData<Boolean>,
            isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
    ) {
        this.query = query
        this.isInitialLoaderVisibleLiveData = isInitialLoaderVisibleLiveData
        this.isNextPageLoaderVisibleLiveData = isNextPageLoaderVisibleLiveData
    }

    override fun create(): DataSource<Int, SearchProductItemViewModel> {
        return ProductDataSource(repository, query, isInitialLoaderVisibleLiveData, isNextPageLoaderVisibleLiveData).apply {
            dataSource = this
        }
    }
}