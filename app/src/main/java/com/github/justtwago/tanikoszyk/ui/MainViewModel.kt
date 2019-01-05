package com.github.justtwago.tanikoszyk.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.justtwago.tanikoszyk.model.domain.Product
import com.github.justtwago.tanikoszyk.model.domain.toViewModel
import com.github.justtwago.tanikoszyk.services.base.BaseRepository
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.subscribeBy


class MainViewModel(private val baseRepository: BaseRepository) : ViewModel() {
    private var disposable: Disposable = Disposables.disposed()
    private val searchProductViewModelsLiveData = MutableLiveData<List<SearchProductItemViewModel>>()

    fun onCreated() {
        getProducts("schab")
    }

    private fun getProducts(query: String) {
        disposable = baseRepository.getProducts(query)
            .subscribeBy(
                onNext = { page ->
                    page.map { Log.d("BLOGPOST", it.toString()) }
                    Log.d("BLOGPOST", "count: ${page.size}")

                    val newList = mutableListOf<SearchProductItemViewModel>()
                    newList.addAll(searchProductViewModelsLiveData.value ?: emptyList())
                    newList.addAll(page.map { it.toViewModel() })
                    searchProductViewModelsLiveData.postValue(newList.toList())
                }
                , onError = {}
            )
    }

    fun getSearchProductViewModelsLiveData(): LiveData<List<SearchProductItemViewModel>> = searchProductViewModelsLiveData

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}