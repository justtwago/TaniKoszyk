package com.tanikoszyk.ui.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.dpToPx
import com.tanikoszyk.common.extensions.inflateChild

class CustomToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    private var extraMenuPadding = 0

    init {
        inflateChild(R.layout.layout_custom_toolbar)
        collectAttributes(attrs)
    }

    public override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        setMeasuredDimension(measuredWidth, toolbarHeight.dpToPx.toInt())
        setPadding(0, 0, extraMenuPadding, 0)
    }

    companion object {
        private const val toolbarHeight = 56f
    }

    private fun collectAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomToolbar, 0, 0)
        extraMenuPadding = typedArray.getDimensionPixelSize(R.styleable.CustomToolbar_extraMenuPadding, 0)
        typedArray.recycle()
    }
}