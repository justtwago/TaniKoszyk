package com.tanikoszyk.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.*
import kotlinx.android.synthetic.main.view_search.view.*


class SearchView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var text: String
        get() = searchEditText.text.toString()
        set(value) {
            searchEditText.setText(value)
        }

    init {
        inflateChild(R.layout.view_search)
        setOnTextChangedListener()
        setupCancelButton()
    }

    fun requestSearchFocus() {
        searchEditText.requestFocusAndShowKeyboard()
    }

    private fun setupCancelButton() {
        cancelButton.setOnClickListener {
            text = ""
            searchEditText.clearFocusAndHideKeyboard()
        }
    }

    private fun setOnTextChangedListener(listener: ((String) -> Unit)? = null) {
        searchEditText.setOnTextChangedListener { text ->
            hideCancelButton(text)
            listener?.invoke(text)
        }
    }

    private fun hideCancelButton(text: String) {
        cancelButton.isVisible = text.isNotBlank()
    }

    fun setOnActionDoneListener(listener: () -> Unit) {
        searchEditText.setOnActionDoneListener(listener)
    }
}