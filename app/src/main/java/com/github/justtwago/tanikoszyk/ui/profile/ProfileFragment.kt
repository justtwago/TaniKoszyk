package com.github.justtwago.tanikoszyk.ui.profile

import android.os.Bundle
import android.view.View
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.common.extensions.Ignored
import com.github.justtwago.tanikoszyk.common.extensions.observe
import com.github.justtwago.tanikoszyk.databinding.FragmentProfileBinding
import com.github.justtwago.tanikoszyk.navigation.NavigationRequest
import com.github.justtwago.tanikoszyk.ui.HostActivity
import com.github.justtwago.tanikoszyk.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val layoutId = R.layout.fragment_profile
    override val viewModel by viewModel<ProfileViewModel>()
    override val hasMenu = false

    override fun setupBindingVariables() {
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