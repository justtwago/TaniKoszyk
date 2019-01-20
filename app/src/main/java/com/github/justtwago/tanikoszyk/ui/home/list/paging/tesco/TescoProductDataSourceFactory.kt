package com.github.justtwago.tanikoszyk.ui.home.list.paging.tesco

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.github.justtwago.service.repositories.TESCO_PAGE_SIZE
import com.github.justtwago.service.repositories.TescoRepository
import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel


class TescoProductDataSourceFactory(
        private val repository: TescoRepository
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
            .setPageSize(TESCO_PAGE_SIZE)
            .setPrefetchDistance(TESCO_PAGE_SIZE - 5)
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
        return TescoProductDataSource(repository, query, isNextPageLoaderVisibleLiveData).apply {
            dataSource = this
        }
    }
}