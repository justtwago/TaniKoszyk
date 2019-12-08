package com.tanikoszyk.ui.cart

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.createDiffObservableList
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.service.repositories.CartRepository
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
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
            cartRepository.observeCartProducts(products::update)
        }
    }

    private fun removeProduct(position: Int) {
        viewModelScope.launch {
            val product = products[position]
            cartRepository.removeProductFromCart(product)
            products.update(products.filterNot { it == product })
        }
    }
}