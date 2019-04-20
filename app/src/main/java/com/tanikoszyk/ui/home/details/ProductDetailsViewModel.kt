package com.tanikoszyk.ui.home.details

import androidx.lifecycle.MutableLiveData
import com.tanikoszyk.common.SingleLiveEvent
import com.tanikoszyk.ui.base.BaseViewModel
import com.tanikoszyk.usecases.model.market.common.Product
import com.tanikoszyk.usecases.usecases.realtimedb.AddProductToCartUseCase

class ProductDetailsViewModel(
    private val addProductToCartUseCase: AddProductToCartUseCase
) : BaseViewModel() {

    val product = MutableLiveData<Product>()
    val onProductLoadingFinishedEvent = SingleLiveEvent<Unit>()
    val onDismissEvent = SingleLiveEvent<Unit>()
    val onProductLoadingFinished: () -> Unit = {
        onProductLoadingFinishedEvent.call()
    }

    fun initialize(product: Product) {
        this.product.value = product
    }

    fun onAddToCartClicked() {
        product.value?.let {
            launch {
                addProductToCartUseCase.execute(it)
            }
        }
    }

    fun onDismissClicked() {
        onDismissEvent.call()
    }
}