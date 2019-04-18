package com.tanikoszyk.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.tanikoszyk.common.MarketsLoadingStatus
import com.tanikoszyk.ui.base.BaseViewModel
import com.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.tanikoszyk.ui.home.list.paging.auchan.AuchanProductDataSourceFactory
import com.tanikoszyk.ui.home.list.paging.biedronka.BiedronkaProductDataSourceFactory
import com.tanikoszyk.ui.home.list.paging.kaufland.KauflandProductDataSourceFactory
import com.tanikoszyk.ui.home.list.paging.tesco.TescoProductDataSourceFactory
import com.tanikoszyk.usecases.model.market.common.Market
import com.tanikoszyk.usecases.model.market.common.Product
import com.tanikoszyk.usecases.model.market.common.SortType
import com.tanikoszyk.usecases.usecases.realtimedb.AddProductToCartUseCase

class HomeViewModel(
    private val auchanProductDataSourceFactory: AuchanProductDataSourceFactory,
    private val biedronkaProductDataSourceFactory: BiedronkaProductDataSourceFactory,
    private val kauflandProductDataSourceFactory: KauflandProductDataSourceFactory,
    private val tescoProductDataSourceFactory: TescoProductDataSourceFactory,
    private val addProductToCartUseCase: AddProductToCartUseCase
) : BaseViewModel() {
    private var query = ""
    private var sortType = SortType.TARGET

    var auchanPagedSearchProductViewModelsLiveData: LiveData<PagedList<SearchProductItemViewModel>>
    var biedronkaPagedSearchProductViewModelsLiveData: LiveData<PagedList<SearchProductItemViewModel>>
    var kauflandPagedSearchProductViewModelsLiveData: LiveData<PagedList<SearchProductItemViewModel>>
    var tescoPagedSearchProductViewModelsLiveData: LiveData<PagedList<SearchProductItemViewModel>>

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
        auchanPagedSearchProductViewModelsLiveData = setupAuchanProductViewModelsLiveData()
        biedronkaPagedSearchProductViewModelsLiveData = setupBiedronkaProductViewModelsLiveData()
        kauflandPagedSearchProductViewModelsLiveData = setupKauflandProductViewModelsLiveData()
        tescoPagedSearchProductViewModelsLiveData = setupTescoProductViewModelsLiveData()
    }

    private fun setupAuchanProductViewModelsLiveData(): LiveData<PagedList<SearchProductItemViewModel>> {
        return auchanProductDataSourceFactory.initialize(
            query = query,
            isNextPageLoaderVisibleLiveData = isNextAuchanPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData,
            onProductClickListener = ::addProductToCart
        )
    }

    private fun setupBiedronkaProductViewModelsLiveData(): LiveData<PagedList<SearchProductItemViewModel>> {
        return biedronkaProductDataSourceFactory.initialize(
            query = query,
            isNextPageLoaderVisibleLiveData = isNextBiedronkaPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData,
            onProductClickListener = ::addProductToCart
        )
    }

    private fun setupKauflandProductViewModelsLiveData(): LiveData<PagedList<SearchProductItemViewModel>> {
        return kauflandProductDataSourceFactory.initialize(
            query = query,
            isNextPageLoaderVisibleLiveData = isNextKauflandPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData,
            onProductClickListener = ::addProductToCart
        )
    }

    private fun setupTescoProductViewModelsLiveData(): LiveData<PagedList<SearchProductItemViewModel>> {
        return tescoProductDataSourceFactory.initialize(
            query = query,
            isNextPageLoaderVisibleLiveData = isNextTescoPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData,
            onProductClickListener = ::addProductToCart
        )
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
            isReset = !isAuchanVisible,
            onProductClickListener = ::addProductToCart
        )
    }

    private fun searchInBiedronka(query: String) {
        biedronkaProductDataSourceFactory.invalidate(
            query = query,
            isNextPageLoaderVisibleLiveData = isNextBiedronkaPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData,
            sortType = sortType,
            isReset = !isBiedronkaVisible,
            onProductClickListener = ::addProductToCart
        )
    }

    private fun searchInKaufland(query: String) {
        kauflandProductDataSourceFactory.invalidate(
            query = query,
            isNextPageLoaderVisibleLiveData = isNextKauflandPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData,
            sortType = sortType,
            isReset = !isKauflandVisible,
            onProductClickListener = ::addProductToCart
        )
    }

    private fun searchInTesco(query: String) {
        tescoProductDataSourceFactory.invalidate(
            query = query,
            isNextPageLoaderVisibleLiveData = isNextTescoPageLoaderVisibleLiveData,
            loadingLiveData = loadingLiveData,
            sortType = sortType,
            isReset = !isTescoVisible,
            onProductClickListener = ::addProductToCart
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

    private fun addProductToCart(product: Product): Boolean {
        launch {
            addProductToCartUseCase.execute(product)
        }
        return true
    }
}