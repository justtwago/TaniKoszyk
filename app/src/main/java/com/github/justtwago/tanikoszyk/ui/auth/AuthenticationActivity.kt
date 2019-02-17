package com.github.justtwago.tanikoszyk.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.ui.base.BaseActivity
import com.github.justtwago.tanikoszyk.ui.launchHostActivity

fun Context.launchAuthenticationActivity(){
    val intent = Intent(this, AuthenticationActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}
class AuthenticationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
    }

    fun navigateToHomeScreen() {
        launchHostActivity()
    }
}