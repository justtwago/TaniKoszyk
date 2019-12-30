package com.tanikoszyk.ui.home.list

import android.view.View
import com.tanikoszyk.databinding.ItemProductGridBinding
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.ui.base.BaseProductViewHolder

class SearchProductViewHolder(private val binding: ItemProductGridBinding) : BaseProductViewHolder(binding.root) {
    fun bind(marketProduct: MarketProduct, onClickListener: (product: MarketProduct, rootView: View) -> Unit) {
        binding.marketProduct = marketProduct
        binding.onClickListener = object : OnProductClickListener {
            override fun onProductClicked(product: MarketProduct, rootView: View) = onClickListener(product, rootView)
        }
    }
}