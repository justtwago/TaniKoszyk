package com.github.justtwago.tanikoszyk.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


abstract class BaseFragment<B : ViewDataBinding> : Fragment(), CoroutineScope {
    protected lateinit var binding: B
    protected abstract val layoutId: Int

    private val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}