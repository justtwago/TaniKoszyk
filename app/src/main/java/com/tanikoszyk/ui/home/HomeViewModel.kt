package com.tanikoszyk.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.fanmountain.domain.Market
import com.fanmountain.domain.MarketProduct
import com.fanmountain.domain.SortType
import com.tanikoszyk.common.MarketsLoadingStatus
import com.tanikoszyk.ui.home.list.paging.auchan.AuchanProductDataSourceFactory
import com.tanikoszyk.ui.home.list.paging.biedronka.BiedronkaProductDataSourceFactory
import com.tanikoszyk.ui.home.list.paging.kaufland.KauflandProductDataSourceFactory
import kotlinx.coroutines.launch

class HomeViewModel(
    private val auchanProductDataSourceFactory: AuchanProductDataSourceFactory,
    private val biedronkaProductDataSourceFactory: BiedronkaProductDataSourceFactory,
    private val kauflandProductDataSourceFactory: KauflandProductDataSourceFactory
) : ViewModel() {

    private var query = ""
    private var sortType = SortType.TARGET

    var auchanPagedSearchProductViewModelsLiveData: LiveData<PagedList<MarketProduct>>
    var biedronkaPagedSearchProductViewModelsLiveData: LiveData<PagedList<MarketProduct>>
    var kauflandPagedSearchProductViewModelsLiveData: LiveData<PagedList<MarketProduct>>

    val isNextAuchanPageLoaderVisibleLiveData = MutableLiveData<Boolean>()
    val isNextBiedronkaPageLoaderVisibleLiveData = MutableLiveData<Boolean>()
    val isNextKauflandPageLoaderVisibleLiveData = MutableLiveData<Boolean>()

    val loadingLiveData = MutableLiveData<MarketsLoadingStatus>()

    private var isAuchanVisible = true
    private var isBiedronkaVisible = true
    private var isKauflandVisible = true

    init {
        loadingLiveData.value = MarketsLoadingStatus()
        auchanPagedSearchProductViewModelsLiveData = setupAuchanProductViewModelsLiveData()
        biedronkaPagedSearchProductViewModelsLiveData = setupBiedronkaProductViewModelsLiveData()
        kauflandPagedSearchProductViewModelsLiveData = setupKauflandProductViewModelsLiveData()
    }

    fun onSearchClicked(query: String) {
        this.query = query
        viewModelScope.launch {
            searchInAuchan(query)
            searchInBiedronka(query)
            searchInKaufland(query)
        }
    }

    fun onSortTypeSelected(sortType: SortType) {
        this.sortType = sortType
        viewModelScope.launch {
            searchInAuchan(query)
            searchInBiedronka(query)
            searchInKaufland(query)
        }
    }

    fun onMarketFilterSelected(market: Market, isSelected: Boolean) {
        when (market) {
            Market.AUCHAN -> isAuchanVisible = isSelected
            Market.BIEDRONKA -> isBiedronkaVisible = isSelected
            Market.KAUFLAND -> isKauflandVisible = isSelected
        }
    }

    private fun setupAuchanProductViewModelsLiveData(): LiveData<PagedList<MarketProduct>> {
        return auchanProductDataSourceFactory.initialize(
            query = query,
            isNextPageLoaderVisibleLiveData = isNextAuchanPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData
        )
    }

    private fun setupBiedronkaProductViewModelsLiveData(): LiveData<PagedList<MarketProduct>> {
        return biedronkaProductDataSourceFactory.initialize(
            query = query,
            isNextPageLoaderVisibleLiveData = isNextBiedronkaPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData
        )
    }

    private fun setupKauflandProductViewModelsLiveData(): LiveData<PagedList<MarketProduct>> {
        return kauflandProductDataSourceFactory.initialize(
            query = query,
            isNextPageLoaderVisibleLiveData = isNextKauflandPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData
        )
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
}