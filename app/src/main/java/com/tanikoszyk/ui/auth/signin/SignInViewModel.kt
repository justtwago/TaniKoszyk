package com.tanikoszyk.ui.auth.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanikoszyk.common.SingleLiveEvent
import com.tanikoszyk.navigation.NavigationRequest
import com.tanikoszyk.ui.auth.CredentialsListener
import com.tanikoszyk.usecases.requests.AuthenticationRequest
import com.tanikoszyk.usecases.requests.Result
import com.tanikoszyk.usecases.usecases.auth.CheckIsUserSignInUseCase
import com.tanikoszyk.usecases.usecases.auth.SignInUseCase
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val checkIsUserSignInUseCase: CheckIsUserSignInUseCase
) : ViewModel(), CredentialsListener {

    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()

    fun initialize() {
        skipAuthenticationIfNeeded()
    }

    val navigationEvent = SingleLiveEvent<NavigationRequest>()

    fun onSignInClicked() {
        if (emailLiveData.value.isNullOrEmpty().not() && passwordLiveData.value.isNullOrEmpty().not()) {
            viewModelScope.launch {
                val isUserSignIn = signIn(emailLiveData.value!!, passwordLiveData.value!!)
                if (isUserSignIn) {
                    navigationEvent.value = NavigationRequest.HOME_SCREEN
                }
            }
        }
    }

    fun onOpenSignUpScreenClicked() {
        navigationEvent.value = NavigationRequest.SIGN_UP_SCREEN
    }

    override fun onEmailTextChanged(email: String) {
        emailLiveData.value = email
    }

    override fun onPasswordTextChanged(password: String) {
        passwordLiveData.value = password
    }

    private fun skipAuthenticationIfNeeded() {
        viewModelScope.launch {
            val isUserSignIn = checkIsUserSignInUseCase.execute()
            if (isUserSignIn) {
                navigationEvent.value = NavigationRequest.HOME_SCREEN
            }
        }
    }

    private suspend fun signIn(email: String, password: String): Boolean {
        val result = signInUseCase.execute(
            AuthenticationRequest(
                email,
                password
            )
        )
        return result is Result.Success
    }
}
