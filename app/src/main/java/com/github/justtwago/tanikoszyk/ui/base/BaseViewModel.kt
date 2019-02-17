package com.github.justtwago.tanikoszyk.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


abstract class BaseViewModel : ViewModel() {
    protected lateinit var coroutineScope: CoroutineScope

    fun initCoroutineScope(coroutineScope: CoroutineScope) {
        this.coroutineScope = coroutineScope
    }

    fun launch(action: suspend () -> Unit) {
        coroutineScope.launch {
            action.invoke()
        }
    }
}