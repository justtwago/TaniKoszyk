package com.tanikoszyk.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.tanikoszyk.common.extensions.Ignored
import com.tanikoszyk.usecases.model.market.common.Product
import com.tanikoszyk.usecases.model.market.common.ProductPage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class BaseProductDataSource(
    private val query: String,
    private val isReset: Boolean,
    private val isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : PageKeyedDataSource<Int, Product>() {

    private var pageCount = 0

    protected abstract suspend fun loadProductPage(page: Int): ProductPage?

    protected abstract fun onFirstProductPageLoaded(isLoaded: Boolean)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Product>
    ) {
        when {
            isReset -> callback.onResult(emptyList(), null, null)
            isQueryValid() -> loadFirstPage(callback)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Product>) {
        loadNextPage(params, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Product>) = Ignored

    private fun loadFirstPage(callback: LoadInitialCallback<Int, Product>) {
        GlobalScope.launch {
            onFirstProductPageLoaded(false)
            val productPage = loadProductPage(1)
            if (productPage != null) {
                pageCount = productPage.pageCount
                val nextPage = if (pageCount == 1) null else 2
                val previousPageKey = null
                callback.onResult(
                    productPage.products,
                    previousPageKey,
                    nextPage
                )
            }
            onFirstProductPageLoaded(true)
        }
    }

    private fun loadNextPage(params: LoadParams<Int>, callback: LoadCallback<Int, Product>) {
        GlobalScope.launch {
            isNextPageLoaderVisibleLiveData.postValue(true)
            val productPage = loadProductPage(page = params.key)
            productPage?.let {
                pageCount = productPage.pageCount
                val nextPage = if (pageCount == params.key) null else params.key + 1
                callback.onResult(
                    productPage.products,
                    nextPage
                )
            }
            isNextPageLoaderVisibleLiveData.postValue(false)
        }
    }

    private fun isQueryValid() = query.trim().length > 2
}