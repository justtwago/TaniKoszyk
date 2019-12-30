package com.tanikoszyk.common.extensions

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflateChild(@LayoutRes layout: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layout, this, attachToRoot)

val Float.dpToPx: Float get() = this * Resources.getSystem().displayMetrics.density