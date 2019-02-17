package com.github.justtwago.tanikoszyk.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.ui.auth.launchAuthenticationActivity
import com.github.justtwago.tanikoszyk.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_host.*

fun Context.launchHostActivity(){
    val intent = Intent(this, HostActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}

class HostActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        setupNavigationBar()
    }

    private fun setupNavigationBar() {
        navigationBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigationHome -> {
                    homeFragmentContainer.visibility = View.VISIBLE
                    cartFragmentContainer.visibility = View.GONE
                    profileFragmentContainer.visibility = View.GONE
                    true
                }
                R.id.navigationCart -> {
                    homeFragmentContainer.visibility = View.GONE
                    cartFragmentContainer.visibility = View.VISIBLE
                    profileFragmentContainer.visibility = View.GONE
                    true
                }
                R.id.navigationProfile -> {
                    homeFragmentContainer.visibility = View.GONE
                    cartFragmentContainer.visibility = View.GONE
                    profileFragmentContainer.visibility = View.VISIBLE
                    true
                }
                else -> false
            }
        }
    }

    fun navigateToAuthenticationScreen(){
        launchAuthenticationActivity()
    }
}