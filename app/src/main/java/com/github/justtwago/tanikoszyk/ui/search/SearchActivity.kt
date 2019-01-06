package com.github.justtwago.tanikoszyk.ui.search

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.common.extensions.observe
import com.github.justtwago.tanikoszyk.databinding.ActivityMainBinding
import com.github.justtwago.tanikoszyk.ui.base.BaseActivity
import com.github.justtwago.tanikoszyk.ui.search.list.SearchProductAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : BaseActivity() {

    private val mainViewModel by viewModel<SearchViewModel>()
    private lateinit var searchProductAdapter: SearchProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .setupViewModel()

        setupRecyclerView()
        setupListeners()
    }

    private fun ActivityMainBinding.setupViewModel() {
        setLifecycleOwner(this@SearchActivity)
        viewModel = mainViewModel
    }

    private fun setupRecyclerView() {
        with(productsRecyclerView) {
            searchProductAdapter = SearchProductAdapter()
            adapter = searchProductAdapter
            layoutManager = LinearLayoutManager(context)
        }
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
    }
}
