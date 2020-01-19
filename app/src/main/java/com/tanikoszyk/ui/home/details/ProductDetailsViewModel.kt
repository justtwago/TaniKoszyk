package com.tanikoszyk.ui.home.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tanikoszyk.common.SingleLiveEvent
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.storage.di.CartStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(
    private val cartStorage: CartStorage
) : ViewModel() {

    val marketProduct = MutableLiveData<MarketProduct>()
    val onProductLoadingFinishedEvent = SingleLiveEvent<Unit>()
    val onDismissEvent = SingleLiveEvent<Unit>()
    val onProductLoadingFinished: () -> Unit = {
        onProductLoadingFinishedEvent.call()
    }

    fun initialize(marketProduct: MarketProduct) {
        this.marketProduct.value = marketProduct
    }

    fun onProductAddedToCart() {
        GlobalScope.launch {
            val product = marketProduct.value ?: return@launch
            cartStorage.saveProduct(product)
        }
    }

    fun onDismissClicked() {
        onDismissEvent.call()
    }
}