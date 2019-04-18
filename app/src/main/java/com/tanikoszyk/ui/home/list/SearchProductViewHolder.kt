package com.tanikoszyk.ui.home.list

import com.tanikoszyk.databinding.ItemProductGridBinding
import com.tanikoszyk.ui.base.BaseProductViewHolder


class SearchProductViewHolder(private val binding: ItemProductGridBinding) : BaseProductViewHolder(binding.root) {
    fun bind(searchProductItemViewModel: SearchProductItemViewModel) {
        binding.viewModel = searchProductItemViewModel
    }
}