package com.tanikoszyk.domain

data class Money(
    val value: Double,
    val currency: String
) {

    fun format() = "$value $currency".replace('.', ',')
}
