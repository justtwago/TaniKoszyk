package com.github.justtwago.tanikoszyk.services.kaufland

import android.content.Context
import com.github.justtwago.tanikoszyk.model.kaufland.KauflandProductPage
import com.github.justtwago.tanikoszyk.services.createRetrofit
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


interface KauflandRepository {
    fun getProducts(searchQuery: String): Single<KauflandProductPage>
}

class KauflandRepositoryImpl(private val context: Context) : KauflandRepository {
    override fun getProducts(searchQuery: String): Single<KauflandProductPage> {
        return createRetrofit(context, "https://www.kaufland.pl/")
            .create(KauflandService::class.java)
            .getProducts(searchQuery)
            .subscribeOn(Schedulers.io())
    }
}