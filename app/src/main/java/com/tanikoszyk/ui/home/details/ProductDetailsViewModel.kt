package com.tanikoszyk.ui.home.details

import androidx.lifecycle.MutableLiveData
import com.tanikoszyk.ui.base.BaseViewModel
import com.tanikoszyk.usecases.model.market.common.Product

class ProductDetailsViewModel : BaseViewModel() {
    val product = MutableLiveData<Product>()

    fun initialize(product: Product) {
        this.product.value = product
    }
}