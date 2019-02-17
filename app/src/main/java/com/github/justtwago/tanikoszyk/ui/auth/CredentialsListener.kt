package com.github.justtwago.tanikoszyk.ui.auth


interface CredentialsListener {
    fun onEmailTextChanged(email: String)
    fun onPasswordTextChanged(password: String)
}