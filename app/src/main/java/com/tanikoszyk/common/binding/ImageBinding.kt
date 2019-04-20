package com.tanikoszyk.common.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.tanikoszyk.R
import com.tanikoszyk.usecases.model.market.common.Market

@BindingAdapter("android:src", "market", requireAll = false)
fun ImageView.setImage(imageUrl: String?, market: Market?) {
    val lazyUrl = GlideUrl(
        imageUrl, LazyHeaders.Builder()
            .addHeader(
                "Referer", when (market) {
                    Market.BIEDRONKA -> "http://www.biedronka.pl/pl/searchhub"
                    else -> ""
                }
            )
            .build()
    )
    Glide.with(context)
        .load(lazyUrl)
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
        Market.TESCO -> R.drawable.logo_tesco_big
        Market.KAUFLAND -> R.drawable.logo_kaufland_big
        Market.AUCHAN -> R.drawable.logo_auchan_big
        Market.BIEDRONKA -> R.drawable.logo_biedronka_big
    }
}