package com.tanikoszyk.ui.cart

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.createDiffObservableList
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.storage.di.CartStorage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import javax.inject.Inject

class CartViewModel @Inject constructor(private val cartStorage: CartStorage) : ViewModel() {

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
        cartStorage.subscribeCartProducts()
            .onEach { products.update(it) }
            .launchIn(viewModelScope)
    }

    private fun removeProduct(position: Int) {
        viewModelScope.launch {
            val marketProduct = products.getOrNull(position) ?: return@launch
            cartStorage.removeProduct(marketProduct.product.url)
        }
    }
}