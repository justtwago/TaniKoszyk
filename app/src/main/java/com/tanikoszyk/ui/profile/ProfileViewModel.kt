package com.tanikoszyk.ui.profile

import com.tanikoszyk.common.SingleLiveEvent
import com.tanikoszyk.navigation.NavigationRequest
import com.tanikoszyk.ui.base.BaseViewModel
import com.tanikoszyk.usecases.usecases.auth.SignOutUseCase


class ProfileViewModel(private val signOutUseCase: SignOutUseCase) : BaseViewModel() {
    val navigationEvent = SingleLiveEvent<NavigationRequest>()

    fun onSignOutClicked() {
        launch {
            signOutUseCase.execute()
            navigationEvent.postValue(NavigationRequest.SIGN_OUT)
        }
    }
}