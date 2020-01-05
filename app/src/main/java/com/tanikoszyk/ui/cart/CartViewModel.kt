package com.tanikoszyk.ui.cart

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModel
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.createDiffObservableList
import com.tanikoszyk.domain.MarketProduct
import me.tatarka.bindingcollectionadapter2.ItemBinding
import javax.inject.Inject

class CartViewModel @Inject constructor(
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

    private fun removeProduct(position: Int) {
        //TODO: remove product from database. See more: https://trello.com/c/DIYzYEGH
    }
}