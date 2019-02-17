package com.github.justtwago.tanikoszyk.common.extensions

import android.content.Context
import android.content.ContextWrapper
import android.graphics.PorterDuff
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.github.justtwago.tanikoszyk.R
import kotlinx.android.synthetic.main.layout_custom_toolbar.view.*

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

fun View.onGlobalLayoutListenerOnce(action: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            action()
            viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    })
}

fun View.setupCustomToolbar(
        @StringRes titleResId: Int? = null,
        title: String? = null,
        @ColorRes textColorResId: Int = R.color.white,
        @ColorRes backgroundColorResId: Int = R.color.colorPrimary,
        showNavigationButton: Boolean = true,
        @DrawableRes navigationButtonResId: Int = R.drawable.ic_back,
        showElevation: Boolean = true
) {
    findViewById<Toolbar>(R.id.customToolbar)
        .setup(
            titleResId,
            title,
            textColorResId,
            backgroundColorResId,
            showNavigationButton,
            navigationButtonResId,
            showElevation
        )
}

fun Toolbar.setup(
        @StringRes titleResId: Int? = null,
        title: String? = null,
        @ColorRes textColorResId: Int = R.color.white,
        @ColorRes backgroundColorResId: Int = R.color.colorPrimary,
        showNavigationButton: Boolean = true,
        @DrawableRes navigationButtonResId: Int = R.drawable.ic_back,
        showElevation: Boolean = true
) {

    val activity = getActivity()
    activity.setSupportActionBar(this)

    val textColor = resColor(textColorResId)
    val backgroundColor = resColor(backgroundColorResId)
    customTitleTextView.setTextColor(textColor)
    setBackgroundColor(backgroundColor)
    customTitle = title ?: titleResId?.let { resString(it) } ?: ""
    activity.supportActionBar?.displayOptions = 0
    elevation = if (showElevation) resDimen(R.dimen.custom_toolbar_elevation) else 0f

    if (showNavigationButton) {
        setNavigationIcon(navigationButtonResId)
        navigationIcon!!.setTint(textColor)
        setNavigationOnClickListener { activity.onBackPressed() }
    }
}

fun View.getActivity(): AppCompatActivity {
    return if (context is AppCompatActivity) {
        context as AppCompatActivity
    } else {
        (context as ContextWrapper).baseContext as AppCompatActivity
    }
}


var Toolbar.customTitle: String
    get() = customTitleTextView.text.toString()
    set(value) {
        customTitleTextView.text = value
    }

fun ImageView.setTintColor(@ColorRes colorRes: Int) {
    setColorFilter(ContextCompat.getColor(context, colorRes), PorterDuff.Mode.SRC_IN)
}

fun MenuItem.onMenuItemActionCollapse(action: () -> Unit) {
    setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
        override fun onMenuItemActionExpand(item: MenuItem) = true

        override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
            action()
            return true
        }
    })
}

fun MenuItem.blockCollapsing(context: Context): Boolean {
    setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
    actionView = View(context)
    setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
        override fun onMenuItemActionExpand(item: MenuItem) = false
        override fun onMenuItemActionCollapse(item: MenuItem) = false
    })
    return false
}

fun TextView.addSimpleTextChangedListener(listener: (string: String) -> Unit) = this.addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable) {
        listener(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Ignored

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Ignored
})