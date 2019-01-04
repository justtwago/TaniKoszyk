package com.github.justtwago.tanikoszyk.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.justtwago.tanikoszyk.services.base.BaseRepository
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables


class MainViewModel(private val baseRepository: BaseRepository) : ViewModel() {
    private var disposable: Disposable = Disposables.disposed()
    val doneLiveData = MutableLiveData<String>()

    fun onCreated() {
        getProducts("cukier")
    }

    private fun getProducts(query: String){
        disposable = baseRepository.getProducts(query)
            .subscribe { page ->
                page.forEach {
                    Log.d(
                        "BLOGPOST",
                        "${it.subtitle}, ${it.title}, ${it.imageUrl}, ${it.oldPrice}, ${it.price}, ${it.quantity}, ${it.market.name}"
                    )
                }
                Log.d("BLOGPOST", "count: ${page.size}")
                doneLiveData.postValue("DONE!")
            }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}