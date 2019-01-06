package com.github.justtwago.tanikoszyk.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.justtwago.tanikoszyk.model.domain.ProductPage
import com.github.justtwago.tanikoszyk.model.domain.toViewModel
import com.github.justtwago.tanikoszyk.services.base.BaseRepository
import com.github.justtwago.tanikoszyk.ui.search.list.SearchProductItemViewModel


class SearchViewModel(private val baseRepository: BaseRepository) : ViewModel() {
    private val searchProductViewModelsLiveData = MutableLiveData<List<SearchProductItemViewModel>>()
    private var query: String = ""
    val isLoaderVisible = MutableLiveData<Boolean>()

    suspend fun onSearchClicked(query: String) {
        this.query = query
        getProducts(query)
    }

    private suspend fun getProducts(query: String) {
        isLoaderVisible.postValue(true)
        saveProducts(baseRepository.getProducts(query), resetList = true)
        isLoaderVisible.postValue(false)
    }

    private fun saveProducts(page: ProductPage, resetList: Boolean) {
        mutableListOf<SearchProductItemViewModel>().apply {
            if (!resetList) addAll(searchProductViewModelsLiveData.value ?: emptyList())
            addAll(page.products.map { it.toViewModel() })
            searchProductViewModelsLiveData.postValue(toList())
        }
    }

    fun getSearchProductViewModelsLiveData(): LiveData<List<SearchProductItemViewModel>> = searchProductViewModelsLiveData
}