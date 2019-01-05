package com.github.justtwago.tanikoszyk.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.common.extensions.observe
import com.github.justtwago.tanikoszyk.databinding.ActivityMainBinding
import com.github.justtwago.tanikoszyk.ui.search.list.SearchProductAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val mainViewModel by viewModel<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .setupViewModel()

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        with(productsRecyclerView) {
            val searchProductAdapter = SearchProductAdapter()
            adapter = searchProductAdapter
            layoutManager = LinearLayoutManager(context)
            mainViewModel.getSearchProductViewModelsLiveData().observe(this@SearchActivity) {
                searchProductAdapter.setData(it)
            }
        }
    }

    private fun ActivityMainBinding.setupViewModel() {
        setLifecycleOwner(this@SearchActivity)
        viewModel = mainViewModel
        mainViewModel.onCreated()
    }
}
