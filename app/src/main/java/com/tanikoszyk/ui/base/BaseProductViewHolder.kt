package com.tanikoszyk.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer


abstract class BaseProductViewHolder(override val containerView: View) : RecyclerView.ViewHolder(
    containerView
), LayoutContainer