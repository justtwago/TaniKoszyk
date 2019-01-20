package com.github.justtwago.tanikoszyk.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.github.justtwago.tanikoszyk.ui.search.list.ProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.search.list.paging.auchan.AuchanProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.search.list.paging.biedronka.BiedronkaProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.search.list.paging.kaufland.KauflandProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.search.list.paging.tesco.TescoProductDataSourceFactory


class SearchViewModel(
        private val auchanProductDataSourceFactory: AuchanProductDataSourceFactory,
        private val biedronkaProductDataSourceFactory: BiedronkaProductDataSourceFactory,
        private val kauflandProductDataSourceFactory: KauflandProductDataSourceFactory,
        private val tescoProductDataSourceFactory: TescoProductDataSourceFactory
) : ViewModel() {
    private var query = ""

    var auchanPagedProductViewModelsLiveData: LiveData<PagedList<ProductItemViewModel>>
    var biedronkaPagedProductViewModelsLiveData: LiveData<PagedList<ProductItemViewModel>>
    var kauflandPagedProductViewModelsLiveData: LiveData<PagedList<ProductItemViewModel>>
    var tescoPagedProductViewModelsLiveData: LiveData<PagedList<ProductItemViewModel>>

    val isNextAuchanPageLoaderVisibleLiveData = MutableLiveData<Boolean>()
    val isNextBiedronkaPageLoaderVisibleLiveData = MutableLiveData<Boolean>()
    val isNextKauflandPageLoaderVisibleLiveData = MutableLiveData<Boolean>()
    val isNextTescoPageLoaderVisibleLiveData = MutableLiveData<Boolean>()

    init {
        auchanPagedProductViewModelsLiveData = setupAuchanProductViewModelsLiveData()
        biedronkaPagedProductViewModelsLiveData = setupBiedronkaProductViewModelsLiveData()
        kauflandPagedProductViewModelsLiveData = setupKauflandProductViewModelsLiveData()
        tescoPagedProductViewModelsLiveData = setupTescoProductViewModelsLiveData()
    }

    private fun setupAuchanProductViewModelsLiveData(): LiveData<PagedList<ProductItemViewModel>> {
        return auchanProductDataSourceFactory.initialize(query, isNextAuchanPageLoaderVisibleLiveData)
    }

    private fun setupBiedronkaProductViewModelsLiveData(): LiveData<PagedList<ProductItemViewModel>> {
        return biedronkaProductDataSourceFactory.initialize(query, isNextBiedronkaPageLoaderVisibleLiveData)
    }

    private fun setupKauflandProductViewModelsLiveData(): LiveData<PagedList<ProductItemViewModel>> {
        return kauflandProductDataSourceFactory.initialize(query, isNextKauflandPageLoaderVisibleLiveData)
    }

    private fun setupTescoProductViewModelsLiveData(): LiveData<PagedList<ProductItemViewModel>> {
        return tescoProductDataSourceFactory.initialize(query, isNextTescoPageLoaderVisibleLiveData)
    }

    fun onSearchClicked(query: String) {
        this.query = query
        auchanProductDataSourceFactory.invalidate(query, isNextAuchanPageLoaderVisibleLiveData)
        biedronkaProductDataSourceFactory.invalidate(query, isNextBiedronkaPageLoaderVisibleLiveData)
        kauflandProductDataSourceFactory.invalidate(query, isNextKauflandPageLoaderVisibleLiveData)
        tescoProductDataSourceFactory.invalidate(query, isNextTescoPageLoaderVisibleLiveData)
    }
}