package com.tanikoszyk.ui.auth.signin

import androidx.lifecycle.MutableLiveData
import com.tanikoszyk.usecases.model.Result
import com.tanikoszyk.common.SingleLiveEvent
import com.tanikoszyk.navigation.NavigationRequest
import com.tanikoszyk.ui.auth.CredentialsListener
import com.tanikoszyk.ui.base.BaseViewModel
import com.tanikoszyk.usecases.model.auth.AuthenticationRequest
import com.tanikoszyk.usecases.usecases.auth.CheckIsUserSignInUseCase
import com.tanikoszyk.usecases.usecases.auth.SignInUseCase


class SignInViewModel(
        private val signInUseCase: SignInUseCase,
        private val checkIsUserSignInUseCase: CheckIsUserSignInUseCase
) : BaseViewModel(), CredentialsListener {
    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()

    fun initialize() {
        skipAuthenticationIfNeeded()
    }

    val navigationEvent = SingleLiveEvent<NavigationRequest>()

    fun onSignInClicked() {
        if (emailLiveData.value.isNullOrEmpty().not() && passwordLiveData.value.isNullOrEmpty().not()) {
            launch {
                val isUserSignIn = signIn(emailLiveData.value!!, passwordLiveData.value!!)
                if (isUserSignIn) {
                    navigationEvent.postValue(NavigationRequest.HOME_SCREEN)
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
        launch {
            val isUserSignIn = checkIsUserSignInUseCase.execute()
            if (isUserSignIn) {
                navigationEvent.postValue(NavigationRequest.HOME_SCREEN)
            }
        }
    }

    private suspend fun signIn(email: String, password: String): Boolean {
        val result = signInUseCase.execute(AuthenticationRequest(email, password))
        return result is Result.Success
    }
}