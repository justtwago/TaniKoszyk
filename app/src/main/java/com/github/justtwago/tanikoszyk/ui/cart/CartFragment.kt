package com.github.justtwago.tanikoszyk.ui.cart

import android.os.Bundle
import android.view.View
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.common.extensions.setupCustomToolbar
import com.github.justtwago.tanikoszyk.databinding.FragmentCartBinding
import com.github.justtwago.tanikoszyk.ui.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : BaseFragment<FragmentCartBinding>() {
    override val layoutId = R.layout.fragment_cart
    override val viewModel by viewModel<CartViewModel>()

    override fun setupBindingVariables() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCustomToolbar(view)
        launch { viewModel.initialize() }
    }

    private fun setupCustomToolbar(view: View) {
        view.setupCustomToolbar(
            titleResId = R.string.title_cart,
            showNavigationButton = false,
            showElevation = true
        )
    }
}