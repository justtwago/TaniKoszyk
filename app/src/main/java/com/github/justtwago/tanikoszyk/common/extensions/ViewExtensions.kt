package com.github.justtwago.tanikoszyk.common.extensions

import androidx.appcompat.widget.SearchView


fun SearchView.setOnQueryTextSubmitListener(listener: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            listener.invoke(query)
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean = false
    })
}