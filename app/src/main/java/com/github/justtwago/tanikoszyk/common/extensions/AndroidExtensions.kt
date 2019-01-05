package com.github.justtwago.tanikoszyk.common.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


inline fun <reified T> LiveData<T>.observe(
        lifecycleOwner: LifecycleOwner,
        crossinline observer: (T) -> Unit
) {
    observe(lifecycleOwner, Observer { observer(it!!) })
}