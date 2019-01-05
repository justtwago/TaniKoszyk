package com.github.justtwago.tanikoszyk.services.kaufland

import android.content.Context
import com.github.justtwago.tanikoszyk.model.kaufland.KauflandProductPage
import com.github.justtwago.tanikoszyk.services.createRetrofit
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

const val KAUFLAND_BASE_URL = "https://www.kaufland.pl/"

interface KauflandRepository {
    fun getProducts(searchQuery: String): Single<KauflandProductPage>
}

class KauflandRepositoryImpl(private val context: Context) : KauflandRepository {
    override fun getProducts(searchQuery: String): Single<KauflandProductPage> {
        return createRetrofit(context, KAUFLAND_BASE_URL)
            .create(KauflandService::class.java)
            .getProducts(searchQuery = searchQuery)
            .subscribeOn(Schedulers.io())
    }
}