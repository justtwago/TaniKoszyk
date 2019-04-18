package com.tanikoszyk.ui.home.list

import android.view.View
import com.tanikoszyk.usecases.model.market.common.Product

interface OnProductClickListener {
    fun onProductClicked(product: Product, rootView: View)
}