package com.tanikoszyk.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tanikoszyk.R
import com.tanikoszyk.ui.launchHostActivity

fun Context.launchAuthenticationActivity(){
    val intent = Intent(this, AuthenticationActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}
class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
    }

    fun navigateToHomeScreen() {
        launchHostActivity()
    }
}