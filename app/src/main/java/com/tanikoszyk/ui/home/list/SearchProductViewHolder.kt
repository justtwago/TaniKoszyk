package com.tanikoszyk.ui.home.list

import com.tanikoszyk.databinding.ItemProductGridBinding
import com.tanikoszyk.ui.base.BaseProductViewHolder
import com.tanikoszyk.usecases.model.market.common.Product

class SearchProductViewHolder(private val binding: ItemProductGridBinding) : BaseProductViewHolder(binding.root) {
    fun bind(product: Product, onClickListener: OnProductClickListener) {
        binding.product = product
        binding.onClickListener = onClickListener
    }
}