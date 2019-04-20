package com.tanikoszyk.ui.auth


interface CredentialsListener {
    fun onEmailTextChanged(email: String)
    fun onPasswordTextChanged(password: String)
}