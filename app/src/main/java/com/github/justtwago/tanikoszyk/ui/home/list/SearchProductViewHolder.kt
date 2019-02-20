package com.github.justtwago.tanikoszyk.ui.home.list

import com.github.justtwago.tanikoszyk.databinding.ItemProductGridBinding
import com.github.justtwago.tanikoszyk.ui.base.BaseProductViewHolder


class SearchProductViewHolder(private val binding: ItemProductGridBinding) : BaseProductViewHolder(binding.root) {
    fun bind(searchProductItemViewModel: SearchProductItemViewModel) {
        binding.viewModel = searchProductItemViewModel
    }
}