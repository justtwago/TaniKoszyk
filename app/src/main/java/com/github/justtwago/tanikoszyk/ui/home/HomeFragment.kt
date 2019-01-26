package com.github.justtwago.tanikoszyk.ui.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.common.extensions.setupCustomToolbar
import com.github.justtwago.tanikoszyk.databinding.FragmentHomeBinding
import com.github.justtwago.tanikoszyk.ui.base.BaseFragment
import com.github.justtwago.tanikoszyk.ui.home.list.SearchProductAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val mainViewModel by viewModel<HomeViewModel>()
    override val layoutId = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = mainViewModel
        setupCustomToolbar(view)
        setupMarketRecyclerViews()
        setupListeners()
    }

    private fun setupCustomToolbar(view: View) {
        view.setupCustomToolbar(
            titleResId = R.string.title_products,
            showNavigationButton = false,
            showElevation = false
        )
    }

    private fun setupMarketRecyclerViews() {
        auchanProductsRecyclerView.initialize()
        biedronkaProductsRecyclerView.initialize()
        kauflandProductsRecyclerView.initialize()
        tescoProductsRecyclerView.initialize()
    }

    private fun RecyclerView.initialize() {
        adapter = SearchProductAdapter()
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    private fun setupListeners() {
        searchView.setOnActionDoneListener {
            if (searchView.text.trim().length > 2) {
                launch { mainViewModel.onSearchClicked(query = searchView.text) }
            }
        }
        mainLayout.setOnClickListener {
            searchView.requestSearchFocus()
        }
        sortView.setOnSortItemSelectedListener(mainViewModel::onSortTypeSelected)
    }
}
