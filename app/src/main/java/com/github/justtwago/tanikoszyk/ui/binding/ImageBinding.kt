package com.github.justtwago.tanikoszyk.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.model.domain.Market


@BindingAdapter("android:src")
fun ImageView.setImage(imageUrl: String) {
    Glide.with(context)
        .load(imageUrl)
        .apply(RequestOptions().placeholder(R.drawable.sample_placeholder))
        .into(this)
}

@BindingAdapter("android:src")
fun ImageView.setLogo(market: Market) {
    Glide.with(context)
        .load(market.getLogoRes())
        .apply(RequestOptions().placeholder(R.drawable.sample_placeholder))
        .into(this)
}

private fun Market.getLogoRes(): Int? {
    return when (this) {
        Market.TESCO -> R.drawable.logo_tesco
        Market.KAUFLAND -> R.drawable.logo_kaufland
        Market.AUCHAN -> R.drawable.logo_auchan
        else -> null
    }
}