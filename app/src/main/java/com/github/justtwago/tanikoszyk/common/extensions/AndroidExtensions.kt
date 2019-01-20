package com.github.justtwago.tanikoszyk.common.extensions

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


inline fun <reified T> LiveData<T>.observe(
        lifecycleOwner: LifecycleOwner,
        crossinline observer: (T) -> Unit
) {
    observe(lifecycleOwner, Observer { observer(it!!) })
}

fun ViewGroup.inflateChild(@LayoutRes layout: Int, attachToRoot: Boolean = true): View = LayoutInflater.from(
    context
).inflate(layout, this, attachToRoot)



val Int.dp: Int
    get() {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }

val Float.dp: Int
    get() {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }