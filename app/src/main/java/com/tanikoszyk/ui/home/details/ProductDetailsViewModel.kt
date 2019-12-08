package com.tanikoszyk.ui.home.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tanikoszyk.common.SingleLiveEvent
import com.tanikoszyk.domain.MarketProduct
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor() : ViewModel() {

    val marketProduct = MutableLiveData<MarketProduct>()
    val onProductLoadingFinishedEvent = SingleLiveEvent<Unit>()
    val onDismissEvent = SingleLiveEvent<Unit>()
    val onProductLoadingFinished: () -> Unit = {
        onProductLoadingFinishedEvent.call()
    }

    fun initialize(marketProduct: MarketProduct) {
        this.marketProduct.value = marketProduct
    }

    fun onDismissClicked() {
        onDismissEvent.call()
    }
}