package com.tanikoszyk.ui.home.list.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.tanikoszyk.databinding.ItemFilterMarketBinding
import com.tanikoszyk.ui.base.AnimatedTransition

class MarketFiltersAdapter(
    private val onItemClicked: (MarketItem) -> Unit
) : ListAdapter<MarketItem, MarketFiltersAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilterMarketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked)
    }

    private object DiffCallback : DiffUtil.ItemCallback<MarketItem>() {
        override fun areItemsTheSame(oldItem: MarketItem, newItem: MarketItem) =
            oldItem.market.ordinal == newItem.market.ordinal

        override fun areContentsTheSame(oldItem: MarketItem, newItem: MarketItem) = oldItem == newItem
    }

    class ViewHolder(private val binding: ItemFilterMarketBinding, private val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(marketItem: MarketItem, onItemClicked: (MarketItem) -> Unit) {
            binding.renderItem(marketItem)
            binding.root.setOnClickListener {
                marketItem.isChecked = !marketItem.isChecked
                binding.renderItem(marketItem)
                onItemClicked.invoke(marketItem)
            }
        }

        @UseExperimental(ExperimentalStdlibApi::class)
        private fun ItemFilterMarketBinding.renderItem(marketItem: MarketItem) {
            TransitionManager.beginDelayedTransition(parent, AnimatedTransition())
            name.text = marketItem.formattedName
            check.isVisible = marketItem.isChecked
        }
    }
}