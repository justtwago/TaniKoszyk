package com.github.justtwago.tanikoszyk.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.service.model.domain.Market

private const val USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36"

@BindingAdapter("android:src", "market", requireAll = false)
fun ImageView.setImage(imageUrl: String?, market: Market?) {
    val lazyUrl = GlideUrl(
        imageUrl, LazyHeaders.Builder()
            .addHeader("User-Agent", USER_AGENT)
            .addHeader("Referer", when(market) {
                Market.BIEDRONKA -> "http://www.biedronka.pl/pl/searchhub"
                else -> ""
            })
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
        Market.TESCO -> R.drawable.logo_tesco
        Market.KAUFLAND -> R.drawable.logo_kaufland
        Market.AUCHAN -> R.drawable.logo_auchan
        Market.BIEDRONKA -> R.drawable.logo_biedronka
    }
}