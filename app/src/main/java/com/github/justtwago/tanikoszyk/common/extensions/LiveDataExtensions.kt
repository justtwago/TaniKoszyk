package com.github.justtwago.tanikoszyk.common.extensions

import androidx.lifecycle.MutableLiveData
import com.github.justtwago.tanikoszyk.common.MarketsLoadingStatus


fun MutableLiveData<MarketsLoadingStatus>.postAuchanReady(isReady: Boolean) {
    value!!.isAuchanProductsReady = isReady
    postValue(value)
}
fun MutableLiveData<MarketsLoadingStatus>.postBiedronkaReady(isReady: Boolean) {
    value!!.isBiedronkaProductsReady = isReady
    postValue(value)
}
fun MutableLiveData<MarketsLoadingStatus>.postKauflandReady(isReady: Boolean) {
    value!!.isKauflandProductsReady = isReady
    postValue(value)
}
fun MutableLiveData<MarketsLoadingStatus>.postTescoReady(isReady: Boolean) {
    value!!.isTescoProductsReady = isReady
    postValue(value)
}