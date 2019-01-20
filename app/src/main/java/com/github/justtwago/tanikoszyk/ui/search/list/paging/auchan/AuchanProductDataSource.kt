package com.github.justtwago.tanikoszyk.ui.search.list.paging.auchan

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.github.justtwago.service.common.Response
import com.github.justtwago.service.model.domain.mapToDomain
import com.github.justtwago.service.repositories.AuchanRepository
import com.github.justtwago.tanikoszyk.common.extensions.Ignored
import com.github.justtwago.tanikoszyk.ui.mappers.toViewModel
import com.github.justtwago.tanikoszyk.ui.search.list.ProductItemViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AuchanProductDataSource(
        private val repository: AuchanRepository,
        private val query: String,
        private val isNextPageLoaderVisibleLiveData: MutableLiveData<Boolean>
) : PageKeyedDataSource<Int, ProductItemViewModel>() {

    private var pageCount = 0

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, ProductItemViewModel>
    ) {
        if (query.trim().length > 2) {
            GlobalScope.launch {
                val response = repository.getProducts(query, page = 1)
                if (response is Response.Success.WithBody) {
                    val productPage = response.body.mapToDomain()
                    pageCount = productPage.pageCount
                    val nextPage = if (pageCount == 1) null else 2
                    val previousPageKey = null
                    callback.onResult(
                        productPage.products.map { it.toViewModel() },
                        previousPageKey,
                        nextPage
                    )
                } //TODO: Error handling
            }
        }
    }

    override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, ProductItemViewModel>
    ) {
        GlobalScope.launch {
            isNextPageLoaderVisibleLiveData.postValue(true)
            val response = repository.getProducts(query, params.key)
            if (response is Response.Success.WithBody) {
                val productPage = response.body.mapToDomain()
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