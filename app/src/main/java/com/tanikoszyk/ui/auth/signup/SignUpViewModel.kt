package com.tanikoszyk.ui.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanikoszyk.common.SingleLiveEvent
import com.tanikoszyk.domain.Result
import com.tanikoszyk.navigation.NavigationRequest
import com.tanikoszyk.ui.auth.CredentialsListener
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val authenticator: com.fanmountain.authentication.Authenticator
) : ViewModel(), CredentialsListener {

    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()

    fun initialize() {
        skipAuthenticationIfNeeded()
    }

    val navigationEvent = SingleLiveEvent<NavigationRequest>()

    fun onSignUpClicked() {
        if (emailLiveData.value.isNullOrEmpty().not() && passwordLiveData.value.isNullOrEmpty().not()) {
            viewModelScope.launch {
                val isUserSignUp = signUp(emailLiveData.value!!, passwordLiveData.value!!)
                if (isUserSignUp) navigationEvent.value = NavigationRequest.HOME_SCREEN
            }
        }
    }

    fun onOpenSignInScreenClicked() {
        navigationEvent.value = NavigationRequest.SIGN_IN_SCREEN
    }

    override fun onEmailTextChanged(email: String) {
        emailLiveData.value = email
    }

    override fun onPasswordTextChanged(password: String) {
        passwordLiveData.value = password
    }

    private fun skipAuthenticationIfNeeded() {
        viewModelScope.launch {
            val isLoggedIn = authenticator.isUserLoggedIn()
            if (isLoggedIn) navigationEvent.value = NavigationRequest.HOME_SCREEN
        }
    }

    private suspend fun signUp(email: String, password: String): Boolean {
        val result = authenticator.signUp(email, password)
        return result is Result.Success
    }
}
