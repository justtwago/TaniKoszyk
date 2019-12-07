package com.tanikoszyk.ui.cart

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fanmountain.domain.MarketProduct
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.createDiffObservableList
import com.tanikoszyk.usecases.usecases.realtimedb.ObserveCartProductsUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.RemoveProductFromCartUseCase
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding

class CartViewModel(
    val observeCartProductsUseCase: ObserveCartProductsUseCase,
    val removeProductFromCartUseCase: RemoveProductFromCartUseCase
) : ViewModel() {

    val products = createDiffObservableList<MarketProduct>(
        areContentsTheSame = { old, new -> old == new },
        areItemsTheSame = { old, new -> old.product.url == new.product.url }
    )
    val productsBinding: ItemBinding<MarketProduct> =
        ItemBinding.of<MarketProduct>(BR.marketProduct, R.layout.item_product_line)

    val onProductRemovedListener: (Int) -> Unit = { position ->
        removeProduct(position)
    }

    init {
        viewModelScope.launch {
            observeCartProductsUseCase.execute(products::update)
        }
    }

    private fun removeProduct(position: Int) {
        viewModelScope.launch {
            val product = products[position]
            removeProductFromCartUseCase.execute(product)
            products.update(products.filterNot { it == product })
        }
    }
}