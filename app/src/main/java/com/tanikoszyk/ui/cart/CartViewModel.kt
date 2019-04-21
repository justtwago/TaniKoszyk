package com.tanikoszyk.ui.cart

import androidx.databinding.library.baseAdapters.BR
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.createDiffObservableList
import com.tanikoszyk.ui.base.BaseViewModel
import com.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.tanikoszyk.common.mappers.toSearchProductViewModel
import com.tanikoszyk.usecases.usecases.realtimedb.ObserveCartProductsUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.RemoveProductFromCartUseCase
import me.tatarka.bindingcollectionadapter2.ItemBinding

class CartViewModel(
    val observeCartProductsUseCase: ObserveCartProductsUseCase,
    val removeProductFromCartUseCase: RemoveProductFromCartUseCase
) : BaseViewModel() {

    val products = createDiffObservableList<SearchProductItemViewModel>(
        areContentsTheSame = { old, new -> old == new },
        areItemsTheSame = { old, new -> old.url == new.url }
    )
    val productsBinding: ItemBinding<SearchProductItemViewModel> =
        ItemBinding.of<SearchProductItemViewModel>(BR.viewModel, R.layout.item_product_line)

    val onProductRemovedListener: (Int) -> Unit = { position ->
        removeProduct(position)
    }

    suspend fun initialize() {
        observeCartProductsUseCase.execute {
            products.update(it.map { product -> product.toSearchProductViewModel() })
        }
    }

    private fun removeProduct(position: Int) {
        val productViewModel = products[position]
        launch {
            removeProductFromCartUseCase.execute(productViewModel.product)
            products.update(products.filter { it != productViewModel })
        }
    }
}