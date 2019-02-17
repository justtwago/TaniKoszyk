package com.github.justtwago.tanikoszyk.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.common.extensions.blockCollapsing
import com.github.justtwago.tanikoszyk.common.extensions.setVisibility
import com.github.justtwago.tanikoszyk.common.extensions.setupCustomToolbar
import com.github.justtwago.tanikoszyk.databinding.FragmentHomeBinding
import com.github.justtwago.tanikoszyk.ui.base.BaseFragment
import com.github.justtwago.tanikoszyk.ui.home.list.SearchProductAdapter
import com.github.justtwago.usecases.model.market.common.Market
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    lateinit var menu: Menu
    private val mainViewModel by viewModel<HomeViewModel>()
    override val layoutId = R.layout.fragment_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filter, menu)
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.auchanItem -> item.run {
                blockCollapsing(requireContext())
                checkMarketItem(Market.AUCHAN, auchanProductsRecyclerView)
            }
            R.id.biedronkaItem -> item.run {
                blockCollapsing(requireContext())
                checkMarketItem(Market.BIEDRONKA, biedronkaProductsRecyclerView)
            }
            R.id.kauflandItem -> item.run {
                blockCollapsing(requireContext())
                checkMarketItem(Market.KAUFLAND, kauflandProductsRecyclerView)
            }
            R.id.tescoItem -> item.run {
                blockCollapsing(requireContext())
                checkMarketItem(Market.TESCO, tescoProductsRecyclerView)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun MenuItem.checkMarketItem(market: Market, recyclerView: RecyclerView): Boolean {
        val auchanItem = menu.findItem(R.id.auchanItem)
        val biedronkaItem = menu.findItem(R.id.biedronkaItem)
        val kauflandItem = menu.findItem(R.id.kauflandItem)
        val tescoItem = menu.findItem(R.id.tescoItem)

        val isLastChecked = listOf(auchanItem, biedronkaItem, kauflandItem, tescoItem)
            .filterNot { it.itemId == itemId }
            .none { it.isChecked }

        if (!isLastChecked) {
            isChecked = !isChecked
            recyclerView.setVisibility(isChecked)
            mainViewModel.onMarketFilterSelected(market, isChecked)
        }
        return false
    }
}
