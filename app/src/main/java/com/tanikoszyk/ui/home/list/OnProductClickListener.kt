package com.tanikoszyk.ui.home.list

import android.view.View
import com.tanikoszyk.domain.MarketProduct

interface OnProductClickListener {
    fun onProductClicked(product: MarketProduct, rootView: View)
}