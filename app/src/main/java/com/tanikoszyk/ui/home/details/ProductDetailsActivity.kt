package com.tanikoszyk.ui.home.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.viewModel
import com.tanikoszyk.databinding.ActivityProductDetailsBinding
import com.tanikoszyk.model.MarketProductDto
import com.tanikoszyk.model.toDomain
import com.tanikoszyk.ui.base.BaseActivity

private const val PRODUCT_KEY = "product_key"
fun Activity.launchProductDetailsActivity(marketProduct: MarketProductDto, vararg sourceView: Pair<View, String>) {
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *sourceView).toBundle()
    Intent(this, ProductDetailsActivity::class.java)
        .putExtra(PRODUCT_KEY, marketProduct)
        .let { startActivity(it, options) }
}

class ProductDetailsActivity : BaseActivity<ActivityProductDetailsBinding>() {

    override val layoutId = R.layout.activity_product_details
    private val viewModel by viewModel<ProductDetailsViewModel>()

    override fun setupBindingVariables(binding: ActivityProductDetailsBinding) {
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportPostponeEnterTransition()
        super.onCreate(savedInstanceState)
        initViewModel()
        registerObservers()
    }

    private fun initViewModel() {
        val marketProduct = intent.getParcelableExtra<MarketProductDto>(PRODUCT_KEY)
        viewModel.initialize(marketProduct.toDomain())
    }

    private fun registerObservers() {
        viewModel.onProductLoadingFinishedEvent.observe(this, Observer {
            supportStartPostponedEnterTransition()
        })
        viewModel.onDismissEvent.observe(this, Observer {
            onBackPressed()
        })
    }
}
