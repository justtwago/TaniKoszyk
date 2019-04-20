package com.tanikoszyk.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.tanikoszyk.common.extensions.Ignored
import com.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.tanikoszyk.common.mappers.toSearchProductViewModel
import com.tanikoszyk.usecases.model.market.common.ProductPage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class BaseProductDataSource(
    private val query: String,
    private val isReset: Boolean,
    private val isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : PageKeyedDataSource<Int, SearchProductItemViewModel>() {

    private var pageCount = 0

    protected abstract suspend fun loadProductPage(page: Int): ProductPage?

    protected abstract fun onFirstProductPageLoaded(isLoaded: Boolean)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SearchProductItemViewModel>
    ) {
        when {
            isReset -> callback.onResult(emptyList(), null, null)
            isQueryValid() -> loadFirstPage(callback)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SearchProductItemViewModel>) {
        loadNextPage(params, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SearchProductItemViewModel>) = Ignored

    private fun loadFirstPage(callback: LoadInitialCallback<Int, SearchProductItemViewModel>) {
        GlobalScope.launch {
            onFirstProductPageLoaded(false)
            val productPage = loadProductPage(1)
            if (productPage != null) {
                pageCount = productPage.pageCount
                val nextPage = if (pageCount == 1) null else 2
                val previousPageKey = null
                callback.onResult(
                    productPage.products.map {
                        it.toSearchProductViewModel()
                    },
                    previousPageKey,
                    nextPage
                )
            }
            onFirstProductPageLoaded(true)
        }
    }

    private fun loadNextPage(params: LoadParams<Int>, callback: LoadCallback<Int, SearchProductItemViewModel>) {
        GlobalScope.launch {
            isNextPageLoaderVisibleLiveData.postValue(true)
            val productPage = loadProductPage(page = params.key)
            productPage?.let {
                pageCount = productPage.pageCount
                val nextPage = if (pageCount == params.key) null else params.key + 1
                callback.onResult(
                    productPage.products.map {
                        it.toSearchProductViewModel()
                    },
                    nextPage
                )
            }
            isNextPageLoaderVisibleLiveData.postValue(false)
        }
    }

    private fun isQueryValid() = query.trim().length > 2
}