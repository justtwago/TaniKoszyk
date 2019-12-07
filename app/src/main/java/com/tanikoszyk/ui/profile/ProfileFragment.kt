package com.tanikoszyk.ui.profile

import android.os.Bundle
import android.view.View
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.Ignored
import com.tanikoszyk.common.extensions.observe
import com.tanikoszyk.databinding.FragmentProfileBinding
import com.tanikoszyk.navigation.NavigationRequest
import com.tanikoszyk.ui.HostActivity
import com.tanikoszyk.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val layoutId = R.layout.fragment_profile
    private val viewModel by viewModel<ProfileViewModel>()

    override fun setupBindingVariables(binding: FragmentProfileBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObservers()
    }

    private fun registerObservers() {
        viewModel.navigationEvent.observe(this) { navigationRequest ->
            when (navigationRequest) {
                NavigationRequest.SIGN_OUT -> navigateToAuthenticationScreen()
                else -> Ignored
            }
        }
    }

    private fun navigateToAuthenticationScreen() {
        (requireActivity() as HostActivity).navigateToAuthenticationScreen()
    }
}