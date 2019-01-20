package com.github.justtwago.tanikoszyk.ui.search.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.github.justtwago.tanikoszyk.common.extensions.Ignored
import com.github.justtwago.service.repositories.ProductRepository
import com.github.justtwago.tanikoszyk.ui.mappers.toViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ProductDataSource(
        private val repository: ProductRepository,
        private val query: String,
        private val isInitialLoaderVisibleLiveData: MutableLiveData<Boolean>,
        private val isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : PageKeyedDataSource<Int, SearchProductItemViewModel>() {

    private var pageCount = 0

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, SearchProductItemViewModel>
    ) {
        if (query.trim().length > 2) {
            GlobalScope.launch {
                isInitialLoaderVisibleLiveData.postValue(true)
                val products = repository.getProducts(query)
                pageCount = products.pageCount
                val nextPage = if (pageCount == 1) null else 2
                val previousPageKey = null
                callback.onResult(
                    products.products.map { it.toViewModel() },
                    previousPageKey,
                    nextPage
                )
                isInitialLoaderVisibleLiveData.postValue(false)
            }
        }
    }

    override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, SearchProductItemViewModel>
    ) {
        GlobalScope.launch {
            isNextPageLoaderVisibleLiveData.postValue(true)
            val products = repository.getProducts(query, params.key)
            pageCount = products.pageCount
            val nextPage = if (pageCount == params.key) null else params.key + 1
            callback.onResult(products.products.map { it.toViewModel() }, nextPage)
            isNextPageLoaderVisibleLiveData.postValue(false)
        }
    }

    override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, SearchProductItemViewModel>
    ) = Ignored
}