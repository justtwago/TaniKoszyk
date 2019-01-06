package com.github.justtwago.tanikoszyk.ui.search.list

import com.github.justtwago.tanikoszyk.databinding.ItemProductBinding


class SearchProductViewHolder(private val binding: ItemProductBinding) : BaseProductViewHolder(binding.root) {
    fun bind(searchProductItemViewModel: SearchProductItemViewModel) {
        binding.viewModel = searchProductItemViewModel
    }
}