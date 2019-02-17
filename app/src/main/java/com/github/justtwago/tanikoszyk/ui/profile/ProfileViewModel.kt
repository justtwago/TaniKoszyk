package com.github.justtwago.tanikoszyk.ui.profile

import androidx.lifecycle.ViewModel
import com.github.justtwago.tanikoszyk.common.SingleLiveEvent
import com.github.justtwago.tanikoszyk.navigation.NavigationRequest
import com.github.justtwago.usecases.usecases.auth.SignOutUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class ProfileViewModel(private val signOutUseCase: SignOutUseCase) : ViewModel() {
    private lateinit var coroutineScope: CoroutineScope
    val navigationEvent = SingleLiveEvent<NavigationRequest>()

    fun initialize(coroutineScope: CoroutineScope) {
        this.coroutineScope = coroutineScope
    }

    fun onSignOutClicked() {
        coroutineScope.launch {
            signOutUseCase.execute()
            navigationEvent.postValue(NavigationRequest.SIGN_OUT)
        }
    }
}