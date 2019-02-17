package com.github.justtwago.tanikoszyk.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.github.justtwago.tanikoszyk.common.MarketsLoadingStatus
import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.home.list.paging.auchan.AuchanProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.home.list.paging.biedronka.BiedronkaProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.home.list.paging.kaufland.KauflandProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.home.list.paging.tesco.TescoProductDataSourceFactory
import com.github.justtwago.usecases.model.market.common.Market
import com.github.justtwago.usecases.model.market.common.SortType

class HomeViewModel(
        private val auchanProductDataSourceFactory: AuchanProductDataSourceFactory,
        private val biedronkaProductDataSourceFactory: BiedronkaProductDataSourceFactory,
        private val kauflandProductDataSourceFactory: KauflandProductDataSourceFactory,
        private val tescoProductDataSourceFactory: TescoProductDataSourceFactory
) : ViewModel() {
    private var query = ""
    private var sortType = SortType.TARGET

    var auchanPagedProductViewModelsLiveData: LiveData<PagedList<ProductItemViewModel>>
    var biedronkaPagedProductViewModelsLiveData: LiveData<PagedList<ProductItemViewModel>>
    var kauflandPagedProductViewModelsLiveData: LiveData<PagedList<ProductItemViewModel>>
    var tescoPagedProductViewModelsLiveData: LiveData<PagedList<ProductItemViewModel>>

    val isNextAuchanPageLoaderVisibleLiveData = MutableLiveData<Boolean>()
    val isNextBiedronkaPageLoaderVisibleLiveData = MutableLiveData<Boolean>()
    val isNextKauflandPageLoaderVisibleLiveData = MutableLiveData<Boolean>()
    val isNextTescoPageLoaderVisibleLiveData = MutableLiveData<Boolean>()

    val loadingLiveData = MutableLiveData<MarketsLoadingStatus>()

    private var isAuchanVisible = true
    private var isBiedronkaVisible = true
    private var isKauflandVisible = true
    private var isTescoVisible = true

    init {
        loadingLiveData.value = MarketsLoadingStatus()
        auchanPagedProductViewModelsLiveData = setupAuchanProductViewModelsLiveData()
        biedronkaPagedProductViewModelsLiveData = setupBiedronkaProductViewModelsLiveData()
        kauflandPagedProductViewModelsLiveData = setupKauflandProductViewModelsLiveData()
        tescoPagedProductViewModelsLiveData = setupTescoProductViewModelsLiveData()
    }

    private fun setupAuchanProductViewModelsLiveData(): LiveData<PagedList<ProductItemViewModel>> {
        return auchanProductDataSourceFactory.initialize(query, isNextAuchanPageLoaderVisibleLiveData, loadingLiveData)
    }

    private fun setupBiedronkaProductViewModelsLiveData(): LiveData<PagedList<ProductItemViewModel>> {
        return biedronkaProductDataSourceFactory.initialize(query, isNextBiedronkaPageLoaderVisibleLiveData, loadingLiveData)
    }

    private fun setupKauflandProductViewModelsLiveData(): LiveData<PagedList<ProductItemViewModel>> {
        return kauflandProductDataSourceFactory.initialize(query, isNextKauflandPageLoaderVisibleLiveData, loadingLiveData)
    }

    private fun setupTescoProductViewModelsLiveData(): LiveData<PagedList<ProductItemViewModel>> {
        return tescoProductDataSourceFactory.initialize(query, isNextTescoPageLoaderVisibleLiveData, loadingLiveData)
    }

    fun onSearchClicked(query: String) {
        this.query = query
        searchInAuchan(query)
        searchInBiedronka(query)
        searchInKaufland(query)
        searchInTesco(query)
    }

    fun onSortTypeSelected(sortType: SortType) {
        this.sortType = sortType
        searchInAuchan(query)
        searchInBiedronka(query)
        searchInKaufland(query)
        searchInTesco(query)
    }

    private fun searchInAuchan(query: String) {
        auchanProductDataSourceFactory.invalidate(
            query = query,
            isNextPageLoaderVisibleLiveData = isNextAuchanPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData,
            sortType = sortType,
            isReset = !isAuchanVisible
        )
    }

    private fun searchInBiedronka(query: String) {
        biedronkaProductDataSourceFactory.invalidate(
            query = query,
            isNextPageLoaderVisibleLiveData = isNextBiedronkaPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData,
            sortType = sortType,
            isReset = !isBiedronkaVisible
        )
    }

    private fun searchInKaufland(query: String) {
        kauflandProductDataSourceFactory.invalidate(
            query = query,
            isNextPageLoaderVisibleLiveData = isNextKauflandPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData,
            sortType = sortType,
            isReset = !isKauflandVisible
        )
    }

    private fun searchInTesco(query: String) {
        tescoProductDataSourceFactory.invalidate(
            query = query,
            isNextPageLoaderVisibleLiveData = isNextTescoPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData,
            sortType = sortType,
            isReset = !isTescoVisible
        )
    }

    fun onMarketFilterSelected(market: Market, isSelected: Boolean) {
        when (market) {
            Market.AUCHAN -> isAuchanVisible = isSelected
            Market.BIEDRONKA -> isBiedronkaVisible = isSelected
            Market.KAUFLAND -> isKauflandVisible = isSelected
            Market.TESCO -> isTescoVisible = isSelected
        }
    }
}