package com.github.justtwago.tanikoszyk.ui.profile

import com.github.justtwago.tanikoszyk.common.SingleLiveEvent
import com.github.justtwago.tanikoszyk.navigation.NavigationRequest
import com.github.justtwago.tanikoszyk.ui.base.BaseViewModel
import com.github.justtwago.usecases.usecases.auth.SignOutUseCase


class ProfileViewModel(private val signOutUseCase: SignOutUseCase) : BaseViewModel() {
    val navigationEvent = SingleLiveEvent<NavigationRequest>()

    fun onSignOutClicked() {
        launch {
            signOutUseCase.execute()
            navigationEvent.postValue(NavigationRequest.SIGN_OUT)
        }
    }
}