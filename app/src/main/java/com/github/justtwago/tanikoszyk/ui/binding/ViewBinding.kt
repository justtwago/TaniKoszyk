package com.github.justtwago.tanikoszyk.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.github.justtwago.tanikoszyk.ui.search.list.SearchProductAdapter
import com.github.justtwago.tanikoszyk.ui.search.list.ProductItemViewModel

@BindingAdapter("android:visibility")
fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("products")
fun RecyclerView.setProducts(products: PagedList<ProductItemViewModel>?){
    (adapter as? SearchProductAdapter)?.submitList(products)
}

@BindingAdapter("loadingItemVisibility")
fun RecyclerView.setLoadingItemVisibility(isVisible: Boolean){
    (adapter as? SearchProductAdapter)?.setLoaderVisibility(isVisible)
}