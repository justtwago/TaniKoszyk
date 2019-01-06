package com.github.justtwago.tanikoszyk.ui.search.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.common.extensions.inflateChild
import com.github.justtwago.tanikoszyk.databinding.ItemProductBinding

private const val PRODUCT_TYPE = 0
private const val LOADER_TYPE = 1

class SearchProductAdapter : PagedListAdapter<SearchProductItemViewModel, BaseProductViewHolder>(
    DIFF_CALLBACK
) {
    private var isProductsLoading: Boolean? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseProductViewHolder {
        return when (viewType) {
            PRODUCT_TYPE -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemProductBinding.inflate(inflater, parent, false)
                SearchProductViewHolder(binding)
            }
            LOADER_TYPE -> {
                LoaderViewHolder(parent.inflateChild(R.layout.item_loading, false))
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: BaseProductViewHolder, position: Int) {
        (holder as? SearchProductViewHolder)?.let {
            getItem(position)?.let(it::bind)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            LOADER_TYPE
        } else {
            PRODUCT_TYPE
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setLoaderVisibility(isLoaderVisible: Boolean) {
        val hadExtraRow = hasExtraRow()
        this.isProductsLoading = isLoaderVisible
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        }
    }

    private fun hasExtraRow() = isProductsLoading != null && isProductsLoading == true

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