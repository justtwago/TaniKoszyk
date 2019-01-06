package com.github.justtwago.tanikoszyk.common.extensions

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView

fun View.requestFocusAndShowKeyboard() {
    requestFocus()

    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.clearFocusAndHideKeyboard() {
    clearFocus()
    hideKeyboard(context, this)
}

fun hideKeyboard(context: Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText.setOnTextChangedListener(textChangedListener: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Ignored

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Ignored

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            textChangedListener.invoke(s.toString())
        }
    })
}

fun TextView.setOnActionDoneListener(listener: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
            listener.invoke()
            clearFocus()
            hideKeyboard(context, this)
        }
        false
    }
}

fun View.setVisibility(isVisible: Boolean) {
    if (isVisible) setVisible() else setGone()
}

fun View.setGone() {
    visibility = View.GONE
}

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setInvisible() {
    visibility = View.INVISIBLE
}