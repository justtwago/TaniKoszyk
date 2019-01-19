package com.github.justtwago.tanikoszyk.common.util


import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class CoroutineContextProvider(
        val ui: CoroutineContext = Dispatchers.Main,
        val background: CoroutineContext = Dispatchers.IO
)