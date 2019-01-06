package com.github.justtwago.tanikoszyk.ui.search.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.justtwago.tanikoszyk.databinding.ItemProductBinding


class SearchProductAdapter : PagedListAdapter<SearchProductItemViewModel, SearchProductViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return SearchProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchProductViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<SearchProductItemViewModel>() {
            override fun areItemsTheSame(
                    oldConcert: SearchProductItemViewModel,
                    newConcert: SearchProductItemViewModel
            ): Boolean = oldConcert.product.id == newConcert.product.id

            override fun areContentsTheSame(
                    oldConcert: SearchProductItemViewModel,
                    newConcert: SearchProductItemViewModel
            ): Boolean = oldConcert == newConcert
        }
    }
}