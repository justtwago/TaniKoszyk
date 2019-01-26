package com.github.justtwago.tanikoszyk.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.github.justtwago.service.model.domain.SortType
import com.github.justtwago.tanikoszyk.common.MarketsLoadingStatus
import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.home.list.paging.auchan.AuchanProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.home.list.paging.biedronka.BiedronkaProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.home.list.paging.kaufland.KauflandProductDataSourceFactory
import com.github.justtwago.tanikoszyk.ui.home.list.paging.tesco.TescoProductDataSourceFactory


class HomeViewModel(
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

    val loadingLiveData = MutableLiveData<MarketsLoadingStatus>()

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
        auchanProductDataSourceFactory.invalidate(query, isNextAuchanPageLoaderVisibleLiveData, loadingLiveData)
        biedronkaProductDataSourceFactory.invalidate(query, isNextBiedronkaPageLoaderVisibleLiveData, loadingLiveData)
        kauflandProductDataSourceFactory.invalidate(query, isNextKauflandPageLoaderVisibleLiveData, loadingLiveData)
        tescoProductDataSourceFactory.invalidate(query, isNextTescoPageLoaderVisibleLiveData, loadingLiveData)
    }

    fun onSortTypeSelected(sortType: SortType) {
        auchanProductDataSourceFactory.invalidate(query, isNextAuchanPageLoaderVisibleLiveData, loadingLiveData, sortType)
        tescoProductDataSourceFactory.invalidate(query, isNextAuchanPageLoaderVisibleLiveData, loadingLiveData, sortType)
        //TODO: Implement sorting for kaufland and biedronka markets
    }
}