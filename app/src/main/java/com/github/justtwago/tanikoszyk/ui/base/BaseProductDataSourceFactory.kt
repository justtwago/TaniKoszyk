package com.github.justtwago.tanikoszyk.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.github.justtwago.tanikoszyk.common.MarketsLoadingStatus
import com.github.justtwago.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.github.justtwago.usecases.model.market.common.Product
import com.github.justtwago.usecases.model.market.common.SortType

abstract class BaseProductDataSourceFactory(private val basePageSize: Int) : DataSource.Factory<Int, SearchProductItemViewModel>() {

    protected lateinit var isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
    protected lateinit var loadingLiveData: MutableLiveData<MarketsLoadingStatus>
    protected lateinit var query: String
    protected lateinit var sortType: SortType
    protected lateinit var onProductClickListener: (Product) -> Boolean
    protected var isReset: Boolean = false
    protected var dataSource: PageKeyedDataSource<Int, SearchProductItemViewModel>? = null

    fun initialize(
        query: String,
        isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>,
        loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
        onProductClickListener: (Product) -> Boolean,
        sortType: SortType = SortType.TARGET,
        isReset: Boolean = false
    ): LiveData<PagedList<SearchProductItemViewModel>> {
        this.query = query
        this.sortType = sortType
        this.onProductClickListener = onProductClickListener
        this.isNextPageLoaderVisibleLiveData = isNextPageLoaderVisibleLiveData
        this.loadingLiveData = loadingLiveData
        this.isReset = isReset

        val config = PagedList.Config.Builder()
            .setPageSize(basePageSize)
            .setPrefetchDistance(basePageSize - 5)
            .build()
        return LivePagedListBuilder<Int, SearchProductItemViewModel>(this, config).build()
    }

    fun invalidate(
        query: String,
        isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>,
        loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
        onProductClickListener: (Product) -> Boolean,
        sortType: SortType = SortType.TARGET,
        isReset: Boolean
    ) {
        initialize(query, isNextPageLoaderVisibleLiveData, loadingLiveData, onProductClickListener, sortType, isReset)
        dataSource?.invalidate()
    }
}