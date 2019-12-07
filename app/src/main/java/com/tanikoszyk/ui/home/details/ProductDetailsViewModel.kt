package com.tanikoszyk.ui.home.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fanmountain.domain.MarketProduct
import com.tanikoszyk.common.SingleLiveEvent
import com.tanikoszyk.usecases.usecases.realtimedb.AddProductToCartUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.CheckIfProductExistsUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.RemoveProductFromCartUseCase

class ProductDetailsViewModel(
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
    private val checkIfProductExistsUseCase: CheckIfProductExistsUseCase
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

    fun onDismissClicked() {
        onDismissEvent.call()
    }
}