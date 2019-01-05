package com.github.justtwago.tanikoszyk.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.common.observe
import com.github.justtwago.tanikoszyk.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModel<MainViewModel>()

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
            mainViewModel.getSearchProductViewModelsLiveData().observe(this@MainActivity) {
                searchProductAdapter.setData(it)
            }
        }
    }

    private fun ActivityMainBinding.setupViewModel() {
        setLifecycleOwner(this@MainActivity)
        viewModel = mainViewModel
        mainViewModel.onCreated()
    }
}
