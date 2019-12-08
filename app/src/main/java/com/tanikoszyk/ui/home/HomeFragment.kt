package com.tanikoszyk.ui.home

import android.os.Bundle
import android.os.SystemClock
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.blockCollapsing
import com.tanikoszyk.common.extensions.setupCustomToolbar
import com.tanikoszyk.common.extensions.viewModel
import com.tanikoszyk.databinding.FragmentHomeBinding
import com.tanikoszyk.domain.Market
import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.model.toDto
import com.tanikoszyk.ui.base.BaseFragment
import com.tanikoszyk.ui.home.details.launchProductDetailsActivity
import com.tanikoszyk.ui.home.list.OnProductClickListener
import com.tanikoszyk.ui.home.list.SearchProductAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel by viewModel<HomeViewModel>()
    override val layoutId = R.layout.fragment_home
    private lateinit var popupMenu: PopupMenu
    private var lastProductItemClickTime: Long = 0

    override fun setupBindingVariables(binding: FragmentHomeBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCustomToolbar(view)
        setupMarketRecyclerViews()
        setupListeners()
        setupPopupMenu()
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
        adapter = SearchProductAdapter(onClickListener = object : OnProductClickListener {
            override fun onProductClicked(product: MarketProduct, rootView: View) {
                if (SystemClock.elapsedRealtime() - lastProductItemClickTime < 1000) return
                lastProductItemClickTime = SystemClock.elapsedRealtime()
                goToDetails(product, rootView)
            }
        })
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    fun goToDetails(product: MarketProduct, rootView: View) {
        requireActivity().launchProductDetailsActivity(product.toDto(), Pair(rootView, rootView.transitionName))
    }

    private fun setupListeners() {
        searchView.setOnActionDoneListener {
            if (searchView.text.trim().length > 2) {
                viewModel.onSearchClicked(query = searchView.text)
            }
        }
        mainLayout.setOnClickListener {
            searchView.requestSearchFocus()
        }
        sortView.setOnSortItemSelectedListener(viewModel::onSortTypeSelected)
        filterMenuItem.setOnClickListener {
            popupMenu.show()
        }
    }

    private fun setupPopupMenu() {
        popupMenu = PopupMenu(requireContext(), filterMenuItem, Gravity.END, R.attr.actionOverflowMenuStyle, 0)
        popupMenu.menuInflater.inflate(R.menu.menu_filter, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.auchanItem -> item.run {
                    blockCollapsing(requireContext())
                    checkMarketItem(popupMenu.menu, Market.AUCHAN, auchanProductsRecyclerView)
                }
                R.id.biedronkaItem -> item.run {
                    blockCollapsing(requireContext())
                    checkMarketItem(popupMenu.menu, Market.BIEDRONKA, biedronkaProductsRecyclerView)
                }
                R.id.kauflandItem -> item.run {
                    blockCollapsing(requireContext())
                    checkMarketItem(popupMenu.menu, Market.KAUFLAND, kauflandProductsRecyclerView)
                }
                else -> false
            }
        }
    }

    private fun MenuItem.checkMarketItem(menu: Menu, market: Market, recyclerView: RecyclerView): Boolean {
        val auchanItem = menu.findItem(R.id.auchanItem)
        val biedronkaItem = menu.findItem(R.id.biedronkaItem)
        val kauflandItem = menu.findItem(R.id.kauflandItem)

        val isLastChecked = listOf(auchanItem, biedronkaItem, kauflandItem)
            .filterNot { it.itemId == itemId }
            .none { it.isChecked }

        if (!isLastChecked) {
            isChecked = !isChecked
            TransitionManager.beginDelayedTransition(mainLayout)
            recyclerView.isVisible = isChecked
            viewModel.onMarketFilterSelected(market, isChecked)
        }
        return false
    }
}
