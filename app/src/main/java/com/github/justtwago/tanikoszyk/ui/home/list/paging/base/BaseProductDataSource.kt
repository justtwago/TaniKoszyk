package com.github.justtwago.tanikoszyk.ui.home.list.paging.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.github.justtwago.service.model.domain.ProductPage
import com.github.justtwago.service.model.domain.SortType
import com.github.justtwago.tanikoszyk.common.extensions.Ignored
import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.mappers.toViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


abstract class BaseProductDataSource(
        private val query: String,
        private val isReset: Boolean,
        private val isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : PageKeyedDataSource<Int, ProductItemViewModel>() {

    private var pageCount = 0

    protected abstract suspend fun loadProductPage(page: Int): ProductPage?

    protected abstract fun allContentProductsReady(isReady: Boolean)

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, ProductItemViewModel>
    ) {
        when {
            isReset -> callback.onResult(emptyList(), null, null)
            isQueryValid() -> GlobalScope.launch {
                allContentProductsReady(false)
                val productPage = loadProductPage(1)
                if (productPage != null) {
                    pageCount = productPage.pageCount
                    val nextPage = if (pageCount == 1) null else 2
                    val previousPageKey = null
                    callback.onResult(
                        productPage.products.map { it.toViewModel() },
                        previousPageKey,
                        nextPage
                    )
                }
                allContentProductsReady(true)
            }
        }
    }

    private fun isQueryValid() = query.trim().length > 2

    override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, ProductItemViewModel>
    ) {
        GlobalScope.launch {
            isNextPageLoaderVisibleLiveData.postValue(true)
            val productPage = loadProductPage(page = params.key)
            productPage?.let {
                pageCount = productPage.pageCount
                val nextPage = if (pageCount == params.key) null else params.key + 1
                callback.onResult(productPage.products.map { it.toViewModel() }, nextPage)
            }
            isNextPageLoaderVisibleLiveData.postValue(false)
        }
    }

    override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, ProductItemViewModel>
    ) = Ignored
}