package com.github.justtwago.tanikoszyk.services.auchan

import android.content.Context
import com.github.justtwago.tanikoszyk.model.auchan.AuchanProductPage
import com.github.justtwago.tanikoszyk.services.createRetrofit
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

const val AUCHAN_BASE_URL = "https://www.auchandirect.pl"

interface AuchanRepository {
    fun getProducts(searchQuery: String): Single<AuchanProductPage>
}

class AuchanRepositoryImpl(private val context: Context) : AuchanRepository {

    override fun getProducts(searchQuery: String): Single<AuchanProductPage> {
        return createRetrofit(context, "$AUCHAN_BASE_URL/")
            .create(AuchanService::class.java)
            .getProducts(searchQuery)
            .subscribeOn(Schedulers.io())
    }
}