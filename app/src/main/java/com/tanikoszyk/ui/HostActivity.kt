package com.tanikoszyk.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.tanikoszyk.R
import kotlinx.android.synthetic.main.activity_host.*

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
                    cartNavHostFragment.hide()
                    homeNavHostFragment.show()
                    true
                }
                R.id.navigationCart -> {
                    homeNavHostFragment.hide()
                    cartNavHostFragment.show()
                    true
                }
                else -> false
            }
        }
        navigationBar.selectedItemId = R.id.navigationHome
    }

    private fun Fragment.show() {
        requireFragmentManager().commit { show(this@show) }
    }

    private fun Fragment.hide() {
        requireFragmentManager().commit { hide(this@hide) }
    }
}