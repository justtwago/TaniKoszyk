package com.github.justtwago.tanikoszyk.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.common.extensions.*
import kotlinx.android.synthetic.main.layout_view_search.view.*


class SearchView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var text: String
        get() = searchEditText.text.toString()
        set(value) {
            searchEditText.setText(value)
        }

    init {
        inflateChild(R.layout.layout_view_search)
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
        cancelButton.setVisibility(text.isNotBlank())
    }

    fun setOnActionDoneListener(listener: () -> Unit) {
        searchEditText.setOnActionDoneListener(listener)
    }
}