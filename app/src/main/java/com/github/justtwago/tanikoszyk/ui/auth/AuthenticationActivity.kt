package com.github.justtwago.tanikoszyk.ui.auth

import android.content.Intent
import android.os.Bundle
import com.github.justtwago.tanikoszyk.common.extensions.addSimpleTextChangedListener
import com.github.justtwago.tanikoszyk.databinding.ActivityAuthenticationBinding
import com.github.justtwago.tanikoszyk.ui.HostActivity
import com.github.justtwago.tanikoszyk.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_authentication.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class AuthenticationActivity : BaseActivity() {

    private val viewModel by viewModel<AuthenticationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        signInButton.setOnClickListener {
            launch {
                val isLoggedIn = viewModel.signIn(loginInput.text.toString(), passwordInput.text.toString())
                if (isLoggedIn) {
                    startActivity(Intent(this@AuthenticationActivity, HostActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                }
            }
        }
    }
}