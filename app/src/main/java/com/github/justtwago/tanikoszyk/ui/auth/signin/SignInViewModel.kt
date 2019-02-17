package com.github.justtwago.tanikoszyk.ui.auth.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.justtwago.usecases.model.Result
import com.github.justtwago.tanikoszyk.common.SingleLiveEvent
import com.github.justtwago.tanikoszyk.navigation.NavigationRequest
import com.github.justtwago.tanikoszyk.ui.auth.CredentialsListener
import com.github.justtwago.usecases.model.auth.AuthenticationRequest
import com.github.justtwago.usecases.usecases.auth.CheckIsUserSignInUseCase
import com.github.justtwago.usecases.usecases.auth.SignInUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class SignInViewModel(
        private val signInUseCase: SignInUseCase,
        private val checkIsUserSignInUseCase: CheckIsUserSignInUseCase
) : ViewModel(), CredentialsListener {
    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()
    private lateinit var coroutineScope: CoroutineScope

    fun initialize(coroutineScope: CoroutineScope) {
        this.coroutineScope = coroutineScope
        skipAuthenticationIfNeeded()
    }

    val navigationEvent = SingleLiveEvent<NavigationRequest>()

    fun onSignInClicked() {
        if (emailLiveData.value.isNullOrEmpty().not() && passwordLiveData.value.isNullOrEmpty().not()) {
            coroutineScope.launch {
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
        coroutineScope.launch {
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
