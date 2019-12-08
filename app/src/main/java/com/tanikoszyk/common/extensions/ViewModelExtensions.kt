package com.tanikoszyk.common.extensions

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.tanikoszyk.di.application.appComponent

inline fun <reified VM : ViewModel> Fragment.viewModel() = viewModels<VM> { appComponent.viewModelFactory }
inline fun <reified VM : ViewModel> ComponentActivity.viewModel() = viewModels<VM> { appComponent.viewModelFactory }