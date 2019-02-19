package com.github.justtwago.tanikoszyk.ui.auth.signin

import android.os.Bundle
import android.view.View
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.databinding.FragmentSignInBinding
import com.github.justtwago.tanikoszyk.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.findNavController
import com.github.justtwago.tanikoszyk.common.extensions.Ignored
import com.github.justtwago.tanikoszyk.common.extensions.observe
import com.github.justtwago.tanikoszyk.navigation.NavigationRequest
import com.github.justtwago.tanikoszyk.ui.auth.AuthenticationActivity


class SignInFragment : BaseFragment<FragmentSignInBinding>() {
    override val layoutId = R.layout.fragment_sign_in
    override val hasMenu = false

    override val viewModel by viewModel<SignInViewModel>()

    override fun setupBindingVariables() {
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