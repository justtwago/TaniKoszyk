package com.tanikoszyk.ui.auth.signup

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.Ignored
import com.tanikoszyk.common.extensions.observe
import com.tanikoszyk.databinding.FragmentSignUpBinding
import com.tanikoszyk.navigation.NavigationRequest
import com.tanikoszyk.ui.auth.AuthenticationActivity
import com.tanikoszyk.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    override val layoutId = R.layout.fragment_sign_up
    override val viewModel by viewModel<SignUpViewModel>()

    override fun setupBindingVariables(binding: FragmentSignUpBinding) {
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
                NavigationRequest.SIGN_IN_SCREEN -> navigateToSignInScreen()
                NavigationRequest.HOME_SCREEN -> navigateToHomeScreen()
                else -> Ignored
            }
        }
    }

    private fun navigateToSignInScreen() {
        requireNotNull(view).findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
    }

    private fun navigateToHomeScreen() {
        (requireActivity() as AuthenticationActivity).navigateToHomeScreen()
    }
}