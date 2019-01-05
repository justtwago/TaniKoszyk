package com.github.justtwago.tanikoszyk.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.justtwago.tanikoszyk.databinding.ItemProductBinding


class SearchProductAdapter : RecyclerView.Adapter<SearchProductViewHolder>() {
    private var searchSearchProductItemViewModels: List<SearchProductItemViewModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return SearchProductViewHolder(binding)
    }

    override fun getItemCount(): Int = searchSearchProductItemViewModels.size

    override fun onBindViewHolder(holder: SearchProductViewHolder, position: Int) {
        holder.bind(searchSearchProductItemViewModels[position])
    }

    fun setData(newSearchProductItemViewModels: List<SearchProductItemViewModel>) {
        val diffCallback = SearchProductItemDiffCallback(
            searchSearchProductItemViewModels,
            newSearchProductItemViewModels
        )
        val diff = DiffUtil.calculateDiff(diffCallback)
        searchSearchProductItemViewModels = newSearchProductItemViewModels
        diff.dispatchUpdatesTo(this)
    }


    class SearchProductItemDiffCallback(
            private val oldList: List<SearchProductItemViewModel>,
            private val newList: List<SearchProductItemViewModel>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].product.id == newList[newItemPosition].product.id
        }

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}