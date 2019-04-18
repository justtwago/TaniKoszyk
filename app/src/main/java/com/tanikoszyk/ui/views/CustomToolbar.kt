package com.tanikoszyk.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.*
import kotlinx.android.synthetic.main.layout_custom_toolbar.view.*


class CustomToolbar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {
    private var isTopPaddingEnabled = true
    private var extraMenuPadding = 0

    init {
        inflateChild(R.layout.layout_custom_toolbar)
        collectAttributes(attrs)
        removeExtraViews()
    }

    private fun removeExtraViews() {
        onGlobalLayoutListenerOnce {
            for (i in 0 until childCount) {
                if (getChildAt(i)?.javaClass == View::class.java) removeViewAt(i)
            }
        }
    }

    public override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (isTopPaddingEnabled) {
            setMeasuredDimension(measuredWidth, toolbarHeight.dp)
            setPadding(0, 0, extraMenuPadding, 0)
        } else {
            setMeasuredDimension(measuredWidth, toolbarHeight.dp)
            setPadding(0, 0, extraMenuPadding, 0)
        }
    }

    companion object {
        private const val toolbarHeight = 56f
    }

    private fun collectAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomToolbar, 0, 0)
        isTopPaddingEnabled = typedArray.getBoolean(R.styleable.CustomToolbar_topPaddingEnabled, true)
        extraMenuPadding = typedArray.getDimensionPixelSize(R.styleable.CustomToolbar_extraMenuPadding, 0)
        typedArray.recycle()
    }

    fun setTitleVisibility(isVisible: Boolean) {
        if (isVisible) {
            customTitleTextView.setVisible()
        } else {
            customTitleTextView.setGone()
        }
    }

    fun setBackButtonColor(color: Int) {
        navigationIcon?.setTint(color)
    }

    fun getMenuItemActionView(itemId: Int): View? {
        return findMenuItem(itemId)?.actionView
    }

    fun findMenuItem(id: Int): MenuItem? {
        return menu?.findItem(id)
    }
}