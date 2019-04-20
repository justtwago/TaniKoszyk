package com.tanikoszyk.common.binding

import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.tanikoszyk.common.extensions.addSimpleTextChangedListener
import com.tanikoszyk.ui.auth.CredentialsListener
import com.tanikoszyk.ui.home.list.SearchProductAdapter
import com.tanikoszyk.ui.home.list.SearchProductItemViewModel

@BindingAdapter("android:visibility")
fun View.setVisibility(isVisible: Boolean) {
    this.isVisible = isVisible
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
fun EditText.setPasswordChangedListener(listener: CredentialsListener) {
    addSimpleTextChangedListener(listener::onPasswordTextChanged)
}