package com.tanikoszyk.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity(), CoroutineScope {
    private lateinit var binding: B
    protected abstract val layoutId: Int
    protected abstract val viewModel: BaseViewModel

    private val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        setupBindingVariables(binding)
        setContentView(binding.root)
        viewModel.initCoroutineScope(this)
    }

    abstract fun setupBindingVariables(binding: B)

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}