package com.tanikoszyk.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import com.tanikoszyk.R
import com.tanikoszyk.ui.auth.launchAuthenticationActivity
import kotlinx.android.synthetic.main.activity_host.*

fun Context.launchHostActivity() {
    val intent = Intent(this, HostActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}

class HostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        setupNavigationBar()
    }

    private fun setupNavigationBar() {
        navigationBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigationHome -> {
                    homeNavHostFragment.show()
                    cartNavHostFragment.hide()
                    profileNavHostFragment.hide()
                    true
                }
                R.id.navigationCart -> {
                    homeNavHostFragment.hide()
                    cartNavHostFragment.show()
                    profileNavHostFragment.hide()
                    true
                }
                R.id.navigationProfile -> {
                    homeNavHostFragment.hide()
                    cartNavHostFragment.hide()
                    profileNavHostFragment.show()
                    true
                }
                else -> false
            }
        }
        navigationBar.selectedItemId = R.id.navigationHome
    }

    fun navigateToAuthenticationScreen() {
        launchAuthenticationActivity()
    }

    private fun Fragment.show() {
        requireFragmentManager().transaction {
            setCustomAnimations(R.anim.animation_show_fragment, R.anim.animation_hide_fragment)
            show(this@show)
        }
    }

    private fun Fragment.hide() {
        requireFragmentManager().transaction { hide(this@hide) }
    }
}