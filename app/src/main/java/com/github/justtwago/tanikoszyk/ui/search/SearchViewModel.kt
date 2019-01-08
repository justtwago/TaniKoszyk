package com.github.justtwago.tanikoszyk.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.github.justtwago.tanikoszyk.services.auchan.AUCHAN_PAGE_SIZE
import com.github.justtwago.tanikoszyk.services.biedronka.BIEDRONKA_PAGE_SIZE
import com.github.justtwago.tanikoszyk.services.kaufland.KAUFLAND_PAGE_SIZE
import com.github.justtwago.tanikoszyk.services.tesco.TESCO_PAGE_SIZE
import com.github.justtwago.tanikoszyk.ui.search.list.ProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.search.list.SearchProductItemViewModel


class SearchViewModel(
        private val productDataSourceFactory: ProductDataSourceFactory
) : ViewModel() {
    private var query = ""

    var pagedProductViewModelsLiveData: LiveData<PagedList<SearchProductItemViewModel>>
    val isNextPageLoaderVisibleLiveData = MutableLiveData<Boolean>()
    val isLoaderVisibleLiveData = MutableLiveData<Boolean>()

    init {
        productDataSourceFactory.initialize(query, isLoaderVisibleLiveData, isNextPageLoaderVisibleLiveData)

        val config = PagedList.Config.Builder()
            .setPageSize(KAUFLAND_PAGE_SIZE + TESCO_PAGE_SIZE + AUCHAN_PAGE_SIZE + BIEDRONKA_PAGE_SIZE)
            .setPrefetchDistance(20)
            .build()
        pagedProductViewModelsLiveData = LivePagedListBuilder<Int, SearchProductItemViewModel>(
            productDataSourceFactory,
            config
        ).build()
    }

    fun onSearchClicked(query: String) {
        this.query = query
        productDataSourceFactory.initialize(query, isLoaderVisibleLiveData, isNextPageLoaderVisibleLiveData)
        productDataSourceFactory.dataSource?.invalidate()
    }
}