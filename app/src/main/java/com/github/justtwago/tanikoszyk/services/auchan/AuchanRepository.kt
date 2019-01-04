package com.github.justtwago.tanikoszyk.services.auchan

import android.content.Context
import com.github.justtwago.tanikoszyk.model.auchan.AuchanProductPage
import com.github.justtwago.tanikoszyk.services.createRetrofit
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


interface AuchanRepository {
    fun getProducts(searchQuery: String): Single<AuchanProductPage>
}

class AuchanRepositoryImpl(private val context: Context) : AuchanRepository {

    override fun getProducts(searchQuery: String): Single<AuchanProductPage> {
        return createRetrofit(context, "https://www.auchandirect.pl/")
            .create(AuchanService::class.java)
            .getProducts(searchQuery)
            .subscribeOn(Schedulers.io())
    }
}