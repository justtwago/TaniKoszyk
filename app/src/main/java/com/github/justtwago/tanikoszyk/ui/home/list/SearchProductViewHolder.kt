package com.github.justtwago.tanikoszyk.ui.home.list

import com.github.justtwago.tanikoszyk.databinding.ItemProductGridBinding


class SearchProductViewHolder(private val binding: ItemProductGridBinding) : BaseProductViewHolder(binding.root) {
    fun bind(productItemViewModel: ProductItemViewModel) {
        binding.viewModel = productItemViewModel
    }
}