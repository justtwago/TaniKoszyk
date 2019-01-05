package com.github.justtwago.tanikoszyk.services.tesco

import android.content.Context
import com.github.justtwago.tanikoszyk.model.tesco.TescoProductPage
import com.github.justtwago.tanikoszyk.services.createRetrofit
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

const val TESCO_BASE_URL = "https://ezakupy.tesco.pl/"

interface TescoRepository {
    fun getProducts(searchQuery: String): Single<TescoProductPage>
}

class TescoRepositoryImpl(private val context: Context) : TescoRepository {
    override fun getProducts(searchQuery: String): Single<TescoProductPage> {
        return createRetrofit(context, TESCO_BASE_URL)
            .create(TescoService::class.java)
            .getProducts(searchQuery)
            .subscribeOn(Schedulers.io())
    }
}