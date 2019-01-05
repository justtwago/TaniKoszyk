package com.github.justtwago.tanikoszyk.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.justtwago.tanikoszyk.common.extensions.Ignored
import com.github.justtwago.tanikoszyk.model.domain.Product
import com.github.justtwago.tanikoszyk.model.domain.toViewModel
import com.github.justtwago.tanikoszyk.services.base.BaseRepository
import com.github.justtwago.tanikoszyk.ui.search.list.SearchProductItemViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.subscribeBy


class SearchViewModel(private val baseRepository: BaseRepository) : ViewModel() {
    private var disposable: Disposable = Disposables.disposed()
    private val searchProductViewModelsLiveData = MutableLiveData<List<SearchProductItemViewModel>>()

    fun onCreated() {
        getProducts("cola")
    }

    private fun getProducts(query: String) {
        disposable = baseRepository.getProducts(query)
            .subscribeBy(
                onNext = ::saveProducts,
                onError = { Ignored }
            )
    }

    private fun saveProducts(page: List<Product>) {
        page.forEach {
            Log.d("ID_PRODUCT", it.id.toString())
        }
        mutableListOf<SearchProductItemViewModel>().apply {
            addAll(searchProductViewModelsLiveData.value ?: emptyList())
            addAll(page.map { it.toViewModel() })
            searchProductViewModelsLiveData.postValue(toList())
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun getSearchProductViewModelsLiveData(): LiveData<List<SearchProductItemViewModel>> = searchProductViewModelsLiveData
}