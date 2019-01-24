package com.github.justtwago.tanikoszyk.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.github.justtwago.tanikoszyk.common.MarketsLoadingStatus
import com.github.justtwago.tanikoszyk.ui.home.list.SearchProductAdapter
import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel

@BindingAdapter("android:visibility")
fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("android:visibility")
fun View.setMarketsContentVisibility(marketsLoadingStatus: MarketsLoadingStatus) {
    visibility = if (marketsLoadingStatus.isContentReady()) View.GONE else View.VISIBLE
}

@BindingAdapter("products")
fun RecyclerView.setProducts(products: PagedList<ProductItemViewModel>?) {
    (adapter as? SearchProductAdapter)?.submitList(products)
}

@BindingAdapter("loadingItemVisibility")
fun RecyclerView.setLoadingItemVisibility(isVisible: Boolean) {
    (adapter as? SearchProductAdapter)?.setLoaderVisibility(isVisible)
}