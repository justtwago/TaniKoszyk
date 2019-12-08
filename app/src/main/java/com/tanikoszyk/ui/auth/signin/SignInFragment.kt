package com.tanikoszyk.ui.auth.signin

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.Ignored
import com.tanikoszyk.common.extensions.viewModel
import com.tanikoszyk.databinding.FragmentSignInBinding
import com.tanikoszyk.navigation.NavigationRequest
import com.tanikoszyk.ui.auth.AuthenticationActivity
import com.tanikoszyk.ui.base.BaseFragment

class SignInFragment : BaseFragment<FragmentSignInBinding>() {
    override val layoutId = R.layout.fragment_sign_in
    private val viewModel by viewModel<SignInViewModel>()

    override fun setupBindingVariables(binding: FragmentSignInBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initialize()
        registerObservers()
    }

    private fun registerObservers() {
        viewModel.navigationEvent.observe(this) { navigationRequest ->
            when (navigationRequest) {
                NavigationRequest.SIGN_UP_SCREEN -> navigateToSignUpScreen()
                NavigationRequest.HOME_SCREEN -> navigateToHomeScreen()
                else -> Ignored
            }
        }
    }

    private fun navigateToSignUpScreen() {
        requireNotNull(view).findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    private fun navigateToHomeScreen() {
        (requireActivity() as AuthenticationActivity).navigateToHomeScreen()
    }
}