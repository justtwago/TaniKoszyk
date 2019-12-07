package com.tanikoszyk.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.fanmountain.domain.MarketProduct
import com.fanmountain.domain.SortType
import com.tanikoszyk.common.MarketsLoadingStatus

abstract class BaseProductDataSourceFactory(private val basePageSize: Int) : DataSource.Factory<Int, MarketProduct>() {

    protected lateinit var isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
    protected lateinit var loadingLiveData: MutableLiveData<MarketsLoadingStatus>
    protected lateinit var query: String
    protected lateinit var sortType: SortType
    protected var isReset: Boolean = false
    protected var dataSource: PageKeyedDataSource<Int, MarketProduct>? = null

    fun initialize(
        query: String,
        isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>,
        loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
        sortType: SortType = SortType.TARGET,
        isReset: Boolean = false
    ): LiveData<PagedList<MarketProduct>> {
        this.query = query
        this.sortType = sortType
        this.isNextPageLoaderVisibleLiveData = isNextPageLoaderVisibleLiveData
        this.loadingLiveData = loadingLiveData
        this.isReset = isReset

        val config = PagedList.Config.Builder()
            .setPageSize(basePageSize)
            .setPrefetchDistance(basePageSize - 5)
            .build()
        return LivePagedListBuilder<Int, MarketProduct>(this, config).build()
    }

    fun invalidate(
        query: String,
        isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>,
        loadingLiveData: MutableLiveData<MarketsLoadingStatus>,
        sortType: SortType = SortType.TARGET,
        isReset: Boolean
    ) {
        initialize(query, isNextPageLoaderVisibleLiveData, loadingLiveData, sortType, isReset)
        dataSource?.invalidate()
    }
}