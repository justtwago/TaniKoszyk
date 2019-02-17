package com.github.justtwago.tanikoszyk.ui.auth

import android.os.Bundle
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.ui.base.BaseActivity
import com.github.justtwago.tanikoszyk.ui.launchHostActivity


class AuthenticationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
    }

    fun navigateToHomeScreen() {
        launchHostActivity()
    }
}