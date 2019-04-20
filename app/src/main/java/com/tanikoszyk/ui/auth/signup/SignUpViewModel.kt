package com.tanikoszyk.ui.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tanikoszyk.common.SingleLiveEvent
import com.tanikoszyk.navigation.NavigationRequest
import com.tanikoszyk.ui.auth.CredentialsListener
import com.tanikoszyk.ui.base.BaseViewModel
import com.tanikoszyk.usecases.model.Result
import com.tanikoszyk.usecases.model.auth.AuthenticationRequest
import com.tanikoszyk.usecases.usecases.auth.CheckIsUserSignInUseCase
import com.tanikoszyk.usecases.usecases.auth.SignUpUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class SignUpViewModel(
        private val signUpUseCase: SignUpUseCase,
        private val checkIsUserSignInUseCase: CheckIsUserSignInUseCase
) : BaseViewModel(), CredentialsListener {
    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()

    fun initialize() {
        skipAuthenticationIfNeeded()
    }

    val navigationEvent = SingleLiveEvent<NavigationRequest>()

    fun onSignUpClicked() {
        if (emailLiveData.value.isNullOrEmpty().not() && passwordLiveData.value.isNullOrEmpty().not()) {
            launch {
                val isUserSignUp = signUp(emailLiveData.value!!, passwordLiveData.value!!)
                if (isUserSignUp) {
                    navigationEvent.postValue(NavigationRequest.HOME_SCREEN)
                }
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
        launch {
            val isUserSignIn = checkIsUserSignInUseCase.execute()
            if (isUserSignIn) {
                navigationEvent.postValue(NavigationRequest.HOME_SCREEN)

            }
        }
    }

    private suspend fun signUp(email: String, password: String): Boolean {
        val result = signUpUseCase.execute(AuthenticationRequest(email, password))
        return result is Result.Success
    }
}
