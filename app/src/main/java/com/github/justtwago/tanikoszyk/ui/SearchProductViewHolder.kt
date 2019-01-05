package com.github.justtwago.tanikoszyk.ui

import androidx.recyclerview.widget.RecyclerView
import com.github.justtwago.tanikoszyk.databinding.ItemProductBinding


class SearchProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(searchProductItemViewModel: SearchProductItemViewModel) {
        binding.viewModel = searchProductItemViewModel
    }
}