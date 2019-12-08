package com.tanikoszyk.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanikoszyk.common.SingleLiveEvent
import com.tanikoszyk.navigation.NavigationRequest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val authenticator: com.fanmountain.authentication.Authenticator) : ViewModel() {
    val navigationEvent = SingleLiveEvent<NavigationRequest>()

    fun onSignOutClicked() {
        viewModelScope.launch {
            authenticator.signOut()
            navigationEvent.value = NavigationRequest.SIGN_OUT
        }
    }
}