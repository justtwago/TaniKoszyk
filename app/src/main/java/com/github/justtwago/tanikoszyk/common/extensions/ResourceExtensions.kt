package com.github.justtwago.tanikoszyk.common.extensions

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat


fun View.resDimen(@DimenRes dimenResId: Int) = context.resources.getDimension(dimenResId)
fun View.resString(@StringRes stringResId: Int, vararg formatArgs: Any) = context.getString(stringResId, *formatArgs)
fun View.resColor(@ColorRes colorResId: Int) = ContextCompat.getColor(context, colorResId)