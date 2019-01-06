package com.github.justtwago.tanikoszyk.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.github.justtwago.tanikoszyk.services.auchan.AUCHAN_PAGE_SIZE
import com.github.justtwago.tanikoszyk.services.kaufland.KAUFLAND_PAGE_SIZE
import com.github.justtwago.tanikoszyk.services.tesco.TESCO_PAGE_SIZE
import com.github.justtwago.tanikoszyk.ui.search.list.ProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.search.list.SearchProductItemViewModel


class SearchViewModel(
        private val productDataSourceFactory: ProductDataSourceFactory
) : ViewModel() {
    private lateinit var pagedProductViewModelsLiveData: LiveData<PagedList<SearchProductItemViewModel>>
    private var query = ""

    val isLoaderVisibleLiveData = MutableLiveData<Boolean>()

    fun initialize(){
        productDataSourceFactory.initialize(query, isLoaderVisibleLiveData)

        val config = PagedList.Config.Builder()
            .setPageSize(KAUFLAND_PAGE_SIZE + TESCO_PAGE_SIZE + AUCHAN_PAGE_SIZE)
            .setPrefetchDistance(20)
            .build()
        pagedProductViewModelsLiveData = LivePagedListBuilder<Int, SearchProductItemViewModel>(
            productDataSourceFactory,
            config
        ).build()
    }

    fun onSearchClicked(query: String) {
        this.query = query
        productDataSourceFactory.initialize(query, isLoaderVisibleLiveData)
        productDataSourceFactory.dataSource?.invalidate()
    }

    fun getPagedProductViewModels(): LiveData<PagedList<SearchProductItemViewModel>> = pagedProductViewModelsLiveData
}