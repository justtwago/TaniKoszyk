package com.github.justtwago.tanikoszyk.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.justtwago.tanikoszyk.model.domain.ProductPage
import com.github.justtwago.tanikoszyk.model.domain.toViewModel
import com.github.justtwago.tanikoszyk.services.base.BaseRepository
import com.github.justtwago.tanikoszyk.ui.search.list.SearchProductItemViewModel


class SearchViewModel(private val baseRepository: BaseRepository) : ViewModel() {
    private val searchProductViewModelsLiveData = MutableLiveData<List<SearchProductItemViewModel>>()

    suspend fun onCreated() {
        getProducts("cukier")
    }

    private suspend fun getProducts(query: String) {
        saveProducts(baseRepository.getProducts(query))
    }

    private fun saveProducts(page: ProductPage) {
        Log.d("PAGES", "Market: ${page.products.first().market.name}, Pages: ${page.pageCount}")
        mutableListOf<SearchProductItemViewModel>().apply {
            addAll(searchProductViewModelsLiveData.value ?: emptyList())
            addAll(page.products.map { it.toViewModel() })
            searchProductViewModelsLiveData.postValue(toList())
        }
    }

    fun getSearchProductViewModelsLiveData(): LiveData<List<SearchProductItemViewModel>> = searchProductViewModelsLiveData
}