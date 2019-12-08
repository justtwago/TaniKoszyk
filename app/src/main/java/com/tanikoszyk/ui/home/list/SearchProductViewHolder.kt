package com.tanikoszyk.ui.home.list

import com.tanikoszyk.databinding.ItemProductGridBinding
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.ui.base.BaseProductViewHolder

class SearchProductViewHolder(private val binding: ItemProductGridBinding) : BaseProductViewHolder(binding.root) {
    fun bind(marketProduct: MarketProduct, onClickListener: OnProductClickListener) {
        binding.marketProduct = marketProduct
        binding.onClickListener = onClickListener
    }
}