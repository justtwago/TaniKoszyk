package com.tanikoszyk.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.tanikoszyk.common.MarketsLoadingStatus
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.domain.SortType
import com.tanikoszyk.ui.home.list.paging.auchan.AuchanProductDataSourceFactory
import com.tanikoszyk.ui.home.list.paging.biedronka.BiedronkaProductDataSourceFactory
import com.tanikoszyk.ui.home.list.paging.kaufland.KauflandProductDataSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val auchanProductDataSourceFactory: AuchanProductDataSourceFactory,
    private val biedronkaProductDataSourceFactory: BiedronkaProductDataSourceFactory,
    private val kauflandProductDataSourceFactory: KauflandProductDataSourceFactory
) : ViewModel() {

    private val searchQueryChannel = Channel<String>(Channel.CONFLATED)
    private val sortTypeChannel = Channel<SortType>(Channel.CONFLATED)

    val auchanProductsLiveData: LiveData<PagedList<MarketProduct>>
    val biedronkaProductsLiveData: LiveData<PagedList<MarketProduct>>
    val kauflandProductsLiveData: LiveData<PagedList<MarketProduct>>

    val isAuchanPageLoaderVisibleLiveData = MutableLiveData<Boolean>()
    val isBiedronkaPageLoaderVisibleLiveData = MutableLiveData<Boolean>()
    val isNextKauflandPageLoaderVisibleLiveData = MutableLiveData<Boolean>()

    val loadingLiveData = MutableLiveData<MarketsLoadingStatus>()

    init {
        loadingLiveData.value = MarketsLoadingStatus()
        auchanProductsLiveData = setupAuchanProductViewModelsLiveData()
        biedronkaProductsLiveData = setupBiedronkaProductViewModelsLiveData()
        kauflandProductsLiveData = setupKauflandProductViewModelsLiveData()
        observeSearchQueryAndSortTypeChanges()
    }

    private fun observeSearchQueryAndSortTypeChanges() {
        searchQueryChannel
            .consumeAsFlow()
            .combine(sortTypeChannel.consumeAsFlow()) { query, sortType -> query to sortType }
            .flowOn(Dispatchers.Default)
            .onEach { (query, sortType) -> searchProducts(query, sortType) }
            .launchIn(viewModelScope)
        onSortTypeSelected(SortType.TARGET)
    }

    private fun searchProducts(query: String, sortType: SortType) {
        viewModelScope.launch {
            searchInAuchan(query, sortType)
            searchInBiedronka(query, sortType)
            searchInKaufland(query, sortType)
        }
    }

    fun onSearchClicked(query: String) {
        if (query.trim().length < 2) return
        viewModelScope.launch {
            searchQueryChannel.send(query)
        }
    }

    fun onSortTypeSelected(sortType: SortType) {
        viewModelScope.launch {
            sortTypeChannel.send(sortType)
        }
    }

    private fun setupAuchanProductViewModelsLiveData(): LiveData<PagedList<MarketProduct>> {
        return auchanProductDataSourceFactory.initialize(
            isPageLoadingLiveData = isAuchanPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData
        )
    }

    private fun setupBiedronkaProductViewModelsLiveData(): LiveData<PagedList<MarketProduct>> {
        return biedronkaProductDataSourceFactory.initialize(
            isPageLoadingLiveData = isBiedronkaPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData
        )
    }

    private fun setupKauflandProductViewModelsLiveData(): LiveData<PagedList<MarketProduct>> {
        return kauflandProductDataSourceFactory.initialize(
            isPageLoadingLiveData = isNextKauflandPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData
        )
    }

    private fun searchInAuchan(query: String, sortType: SortType) {
        auchanProductDataSourceFactory.invalidate(query, sortType)
    }

    private fun searchInBiedronka(query: String, sortType: SortType) {
        biedronkaProductDataSourceFactory.invalidate(query, sortType)
    }

    private fun searchInKaufland(query: String, sortType: SortType) {
        kauflandProductDataSourceFactory.invalidate(query, sortType)
    }

    override fun onCleared() {
        searchQueryChannel.close()
        sortTypeChannel.close()
        super.onCleared()
    }
}