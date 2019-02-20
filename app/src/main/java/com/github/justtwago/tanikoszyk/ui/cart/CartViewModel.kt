package com.github.justtwago.tanikoszyk.ui.cart

import androidx.databinding.library.baseAdapters.BR
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.common.extensions.createDiffObservableList
import com.github.justtwago.tanikoszyk.ui.base.BaseViewModel
import com.github.justtwago.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.github.justtwago.tanikoszyk.ui.mappers.toSearchProductViewModel
import com.github.justtwago.usecases.usecases.realtimedb.ObserveCartProductsUseCase
import me.tatarka.bindingcollectionadapter2.ItemBinding

class CartViewModel(val observeCartProductsUseCase: ObserveCartProductsUseCase) : BaseViewModel() {
    val products = createDiffObservableList<SearchProductItemViewModel>(
        areContentsTheSame = { old, new -> old == new },
        areItemsTheSame = { old, new -> old.id == new.id }
    )
    val productsBinding: ItemBinding<SearchProductItemViewModel> =
        ItemBinding.of<SearchProductItemViewModel>(BR.viewModel, R.layout.item_product_line)

    suspend fun initialize() {
        observeCartProductsUseCase.execute {
            products.update(it.map { product -> product.toSearchProductViewModel(false) { true } })
        }
    }
}