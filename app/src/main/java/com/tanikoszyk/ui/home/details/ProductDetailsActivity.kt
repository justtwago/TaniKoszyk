package com.tanikoszyk.ui.home.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.transition.doOnEnd
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.transition.*
import com.tanikoszyk.R
import com.tanikoszyk.databinding.ActivityProductDetailsBinding
import com.tanikoszyk.ui.base.BaseActivity
import com.tanikoszyk.usecases.model.market.common.Product
import kotlinx.android.synthetic.main.activity_product_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val PRODUCT_KEY = "product_key"
fun Activity.launchProductDetailsActivity(product: Product, vararg sourceView: Pair<View, String>) {
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *sourceView).toBundle()
    Intent(this, ProductDetailsActivity::class.java)
        .putExtra(PRODUCT_KEY, product)
        .let { startActivity(it, options) }
}

class ProductDetailsActivity : BaseActivity<ActivityProductDetailsBinding>() {

    override val layoutId = R.layout.activity_product_details
    override val viewModel by viewModel<ProductDetailsViewModel>()

    override fun setupBindingVariables(binding: ActivityProductDetailsBinding) {
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportPostponeEnterTransition()
        super.onCreate(savedInstanceState)
        initButtonVisibilityAfterTransition()
        initViewModel()
        registerObservers()
    }

    private fun initButtonVisibilityAfterTransition() {
        window.sharedElementEnterTransition.apply {
            duration = 250
            doOnEnd {
                TransitionManager.beginDelayedTransition(rootLayout)
                addToCartButton.isVisible = true
            }
        }
    }

    private fun initViewModel(): Product {
        val product = intent.getParcelableExtra<Product>(PRODUCT_KEY)
        viewModel.initialize(product)
        return product
    }

    private fun registerObservers() {
        viewModel.onProductLoadingFinishedEvent.observe(this, Observer {
            supportStartPostponedEnterTransition()
        })
        viewModel.onDismissEvent.observe(this, Observer {
            onBackPressed()
        })
    }

    override fun onBackPressed() {
        addToCartButton.isVisible = false
        super.onBackPressed()
    }
}
