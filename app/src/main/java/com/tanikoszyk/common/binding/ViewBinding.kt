package com.tanikoszyk.common.binding

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.ui.home.list.SearchProductAdapter

@BindingAdapter("android:visibility")
fun View.setVisibility(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("pagedProducts")
fun RecyclerView.setPagedProducts(searchProducts: PagedList<MarketProduct>?) {
    (adapter as? SearchProductAdapter)?.submitList(searchProducts)
}

@BindingAdapter("loadingItemVisibility")
fun RecyclerView.setLoadingItemVisibility(isVisible: Boolean) {
    (adapter as? SearchProductAdapter)?.setLoaderVisibility(isVisible)
}

@BindingAdapter("onItemRemovedListener")
fun RecyclerView.setOnItemRemoved(onItemRemoved: (position: Int) -> Unit) {
    val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            onItemRemoved.invoke(viewHolder.adapterPosition)
        }
    }
    ItemTouchHelper(simpleCallback).attachToRecyclerView(this)
}