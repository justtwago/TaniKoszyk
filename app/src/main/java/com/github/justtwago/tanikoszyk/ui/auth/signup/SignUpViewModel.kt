package com.github.justtwago.tanikoszyk.ui.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.justtwago.service.common.Result
import com.github.justtwago.service.firebase.FirebaseAuthenticator
import com.github.justtwago.tanikoszyk.common.SingleLiveEvent
import com.github.justtwago.tanikoszyk.navigation.NavigationRequest
import com.github.justtwago.tanikoszyk.ui.auth.CredentialsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class SignUpViewModel(private val authenticator: FirebaseAuthenticator) : ViewModel(), CredentialsListener {
    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()
    private lateinit var coroutineScope: CoroutineScope

    fun initialize(coroutineScope: CoroutineScope) {
        this.coroutineScope = coroutineScope
        skipAuthenticationIfNeeded()
    }

    val navigationEvent = SingleLiveEvent<NavigationRequest>()

    fun onSignUpClicked() {
        if (emailLiveData.value.isNullOrEmpty().not() && passwordLiveData.value.isNullOrEmpty().not()) {
            coroutineScope.launch {
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
        coroutineScope.launch {
            val isUserSignIn = authenticator.isUserLoggedIn()
            if (isUserSignIn) {
                navigationEvent.postValue(NavigationRequest.HOME_SCREEN)

            }
        }
    }

    private suspend fun signUp(email: String, password: String): Boolean {
        val result = authenticator.signUp(email, password)
        return result is Result.Success
    }
}
