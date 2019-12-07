package com.tanikoszyk.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.fanmountain.domain.MarketProduct
import com.fanmountain.domain.ProductPage
import com.tanikoszyk.common.extensions.Ignored
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class BaseProductDataSource(
    private val query: String,
    private val isReset: Boolean,
    private val isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : PageKeyedDataSource<Int, MarketProduct>() {

    private var pageCount = 0

    protected abstract suspend fun loadProductPage(page: Int): ProductPage?

    protected abstract fun onFirstProductPageLoaded(isLoaded: Boolean)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MarketProduct>
    ) {
        when {
            isReset -> callback.onResult(emptyList(), null, null)
            isQueryValid() -> loadFirstPage(callback)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MarketProduct>) {
        loadNextPage(params, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MarketProduct>) = Ignored

    private fun loadFirstPage(callback: LoadInitialCallback<Int, MarketProduct>) {
        GlobalScope.launch {
            onFirstProductPageLoaded(false)
            val productPage = loadProductPage(1)
            if (productPage != null) {
                pageCount = productPage.pageCount
                val nextPage = if (pageCount == 1) null else 2
                val previousPageKey = null
                callback.onResult(
                    productPage.marketProducts,
                    previousPageKey,
                    nextPage
                )
            }
            onFirstProductPageLoaded(true)
        }
    }

    private fun loadNextPage(params: LoadParams<Int>, callback: LoadCallback<Int, MarketProduct>) {
        GlobalScope.launch {
            isNextPageLoaderVisibleLiveData.postValue(true)
            val productPage = loadProductPage(page = params.key)
            productPage?.let {
                pageCount = productPage.pageCount
                val nextPage = if (pageCount == params.key) null else params.key + 1
                callback.onResult(
                    productPage.marketProducts,
                    nextPage
                )
            }
            isNextPageLoaderVisibleLiveData.postValue(false)
        }
    }

    private fun isQueryValid() = query.trim().length > 2
}