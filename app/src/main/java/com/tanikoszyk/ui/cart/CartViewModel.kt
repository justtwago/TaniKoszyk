package com.tanikoszyk.ui.cart

import androidx.databinding.library.baseAdapters.BR
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.createDiffObservableList
import com.tanikoszyk.ui.base.BaseViewModel
import com.tanikoszyk.usecases.model.market.common.Product
import com.tanikoszyk.usecases.usecases.realtimedb.ObserveCartProductsUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.RemoveProductFromCartUseCase
import me.tatarka.bindingcollectionadapter2.ItemBinding

class CartViewModel(
    val observeCartProductsUseCase: ObserveCartProductsUseCase,
    val removeProductFromCartUseCase: RemoveProductFromCartUseCase
) : BaseViewModel() {

    val products = createDiffObservableList<Product>(
        areContentsTheSame = { old, new -> old == new },
        areItemsTheSame = { old, new -> old.url == new.url }
    )
    val productsBinding: ItemBinding<Product> =
        ItemBinding.of<Product>(BR.product, R.layout.item_product_line)

    val onProductRemovedListener: (Int) -> Unit = { position ->
        removeProduct(position)
    }

    suspend fun initialize() {
        observeCartProductsUseCase.execute(products::update)
    }

    private fun removeProduct(position: Int) {
        launch {
            val product = products[position]
            removeProductFromCartUseCase.execute(product)
            products.update(products.filterNot { it == product })
        }
    }
}