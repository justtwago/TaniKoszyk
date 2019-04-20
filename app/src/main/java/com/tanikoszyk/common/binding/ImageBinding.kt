package com.tanikoszyk.common.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.tanikoszyk.R
import com.tanikoszyk.usecases.model.market.common.Market

@BindingAdapter("android:src", "market", "loadingFinishedListener", requireAll = false)
fun ImageView.setImage(imageUrl: String?, market: Market?, onLoadingFinished: (() -> Unit)?) {
    val lazyUrl = buildUrl(imageUrl, market)
    Glide.with(context)
        .load(lazyUrl)
        .listener(onLoadingFinished?.let(::createRequestListener))
        .apply(RequestOptions().placeholder(R.drawable.sample_placeholder))
        .into(this)
}

private fun buildUrl(imageUrl: String?, market: Market?): GlideUrl? {
    return imageUrl?.let {
        GlideUrl(
            it, LazyHeaders.Builder()
                .addHeader(
                    "Referer", when (market) {
                        Market.BIEDRONKA -> "http://www.biedronka.pl/pl/searchhub"
                        else -> ""
                    }
                )
                .build()
        )
    }
}

@BindingAdapter("android:src")
fun ImageView.setLogo(market: Market?) {
    Glide.with(context)
        .load(market?.getLogoRes())
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

private fun createRequestListener(onLoadingFinished: () -> Unit): RequestListener<Drawable> {
    return object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }
    }
}