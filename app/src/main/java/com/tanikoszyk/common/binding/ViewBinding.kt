package com.tanikoszyk.common.binding

import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.tanikoszyk.R
import com.tanikoszyk.common.MarketsLoadingStatus
import com.tanikoszyk.common.extensions.addSimpleTextChangedListener
import com.tanikoszyk.common.extensions.resDrawable
import com.tanikoszyk.ui.auth.CredentialsListener
import com.tanikoszyk.ui.home.list.SearchProductAdapter
import com.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.tanikoszyk.usecases.model.market.common.Product

@BindingAdapter("android:visibility")
fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("android:visibility")
fun View.setMarketsContentVisibility(marketsLoadingStatus: MarketsLoadingStatus) {
    visibility = if (marketsLoadingStatus.isContentReady()) View.GONE else View.VISIBLE
}

@BindingAdapter("pagedProducts")
fun RecyclerView.setPagedProducts(searchProducts: PagedList<SearchProductItemViewModel>?) {
    (adapter as? SearchProductAdapter)?.submitList(searchProducts)
}

@BindingAdapter("loadingItemVisibility")
fun RecyclerView.setLoadingItemVisibility(isVisible: Boolean) {
    (adapter as? SearchProductAdapter)?.setLoaderVisibility(isVisible)
}

@BindingAdapter("emailChangedListener")
fun EditText.setEmailChangedListener(listener: CredentialsListener) {
    addSimpleTextChangedListener(listener::onEmailTextChanged)
}

@BindingAdapter("passwordChangedListener")
fun EditText.setPasswodChangedListener(listener: CredentialsListener) {
    addSimpleTextChangedListener(listener::onPasswordTextChanged)
}

@BindingAdapter("onClick", "product")
fun ConstraintLayout.setOnProductClickListener(listener: (Product) -> Boolean, product: Product) {
    listener.invoke(product)
}

@BindingAdapter("productBackground")
fun ConstraintLayout.setProductBackground(isProductInCart: Boolean) {
    background = resDrawable(
        if (isProductInCart) {
            R.drawable.product_item_background_checked
        } else {
            R.drawable.product_item_background
        }
    )
}