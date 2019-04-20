package com.tanikoszyk.ui.home.details

import androidx.lifecycle.MutableLiveData
import com.tanikoszyk.common.SingleLiveEvent
import com.tanikoszyk.ui.base.BaseViewModel
import com.tanikoszyk.usecases.model.market.common.Product
import com.tanikoszyk.usecases.usecases.realtimedb.AddProductToCartUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.CheckIfProductExistsUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.RemoveProductFromCartUseCase

class ProductDetailsViewModel(
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
    private val checkIfProductExistsUseCase: CheckIfProductExistsUseCase
) : BaseViewModel() {

    val product = MutableLiveData<Product>()
    val onProductLoadingFinishedEvent = SingleLiveEvent<Unit>()
    val onDismissEvent = SingleLiveEvent<Unit>()
    val onProductLoadingFinished: () -> Unit = {
        onProductLoadingFinishedEvent.call()
    }

    fun initialize(product: Product) {
        this.product.value = product
        launch {
            this.product.postValue(
                product.copy(isSelected = checkIfProductExistsUseCase.execute(product))
            )
        }
    }

    fun onSetSelectionClicked() {
        product.value = product.value?.let { it.copy(isSelected = !it.isSelected) }
        product.value?.let {
            launch {
                if (it.isSelected) {
                    addProductToCartUseCase.execute(it)
                } else {
                    removeProductFromCartUseCase.execute(it)
                }
            }
        }
    }

    fun onDismissClicked() {
        onDismissEvent.call()
    }
}