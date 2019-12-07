package com.tanikoszyk.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanikoszyk.common.SingleLiveEvent
import com.tanikoszyk.navigation.NavigationRequest
import com.tanikoszyk.usecases.usecases.auth.SignOutUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(private val signOutUseCase: SignOutUseCase) : ViewModel() {
    val navigationEvent = SingleLiveEvent<NavigationRequest>()

    fun onSignOutClicked() {
        viewModelScope.launch {
            signOutUseCase.execute()
            navigationEvent.value = NavigationRequest.SIGN_OUT
        }
    }
}