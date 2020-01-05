package com.tanikoszyk.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.resDimensions
import com.tanikoszyk.common.extensions.setupCustomToolbar
import com.tanikoszyk.common.extensions.viewModel
import com.tanikoszyk.databinding.FragmentHomeBinding
import com.tanikoszyk.domain.Market
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.model.toDto
import com.tanikoszyk.ui.base.AnimatedTransition
import com.tanikoszyk.ui.base.BaseFragment
import com.tanikoszyk.ui.home.details.launchProductDetailsActivity
import com.tanikoszyk.ui.home.list.SearchProductAdapter
import com.tanikoszyk.ui.home.list.filter.MarketFiltersAdapter
import com.tanikoszyk.ui.home.list.filter.MarketItem
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutId = R.layout.fragment_home
    private val viewModel by viewModel<HomeViewModel>()

    private val marketFiltersAdapter by lazy {
        MarketFiltersAdapter(onItemClicked = ::renderMarketsVisibility)
    }

    override fun setupBindingVariables(binding: FragmentHomeBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCustomToolbar(view)
        setupMarketRecyclerViews()
        setupMarketFiltersRecyclerView()
        setupListeners()
    }

    private fun renderMarketsVisibility(item: MarketItem) {
        when (item.market) {
            Market.AUCHAN -> auchanProductsRecyclerView.isVisible = item.isChecked
            Market.BIEDRONKA -> biedronkaProductsRecyclerView.isVisible = item.isChecked
            Market.KAUFLAND -> kauflandProductsRecyclerView.isVisible = item.isChecked
        }
    }

    private fun setupMarketFiltersRecyclerView() {
        marketFilters.adapter = marketFiltersAdapter.apply {
            val filters = Market.values().toList().map { MarketItem(it, true) }
            submitList(filters)
        }
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
    }

    private fun RecyclerView.initialize() {
        adapter = SearchProductAdapter(onClickListener = { product, rootView -> goToDetails(product, rootView) })
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        itemAnimator = null
    }

    private fun goToDetails(product: MarketProduct, rootView: View) {
        requireActivity().launchProductDetailsActivity(product.toDto(), Pair(rootView, rootView.transitionName))
    }

    private fun setupListeners() {
        searchView.setOnActionDoneListener {
            if (searchView.text.trim().length > 2) {
                viewModel.onSearchClicked(query = searchView.text)
            }
        }
        filterLayout.setOnClickListener {
            val isFiltersVisible = marketFilters.isVisible
            TransitionManager.beginDelayedTransition(mainLayout, AnimatedTransition())
            doneMenuItem.isVisible = !isFiltersVisible
            filterLayout.radius = if (isFiltersVisible) {
                resDimensions(R.dimen.app_corner_radius)
            } else {
                filterLayout.width.toFloat()
            }
            marketFilters.isVisible = !isFiltersVisible
            searchSettingsSpace.isVisible = isFiltersVisible
        }
    }
}
