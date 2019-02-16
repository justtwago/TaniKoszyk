package com.github.justtwago.tanikoszyk.ui.auth


interface CredentialsListener {
    fun onLoginChanged(login: String)
    fun onPasswordChanged(password: String)
}