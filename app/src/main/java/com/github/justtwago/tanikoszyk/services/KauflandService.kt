package com.github.justtwago.tanikoszyk.services

import android.util.Log
import com.github.justtwago.tanikoszyk.Product
import com.github.justtwago.tanikoszyk.ProductPage
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface KauflandService {
    @GET("wyszukiwarka.html")
    fun getProducts(@Query("q") searchQuery: String): Single<ProductPage>
}

interface KauflandRepository {
    fun getProducts(searchQuery: String): Single<ProductPage>
}

class KauflandRepositoryImpl : KauflandRepository {
    override fun getProducts(searchQuery: String): Single<ProductPage> {
        return createRetrofit()
            .create(KauflandService::class.java)
            .getProducts(searchQuery)
            .subscribeOn(Schedulers.io())
    }

    private fun createRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://www.kaufland.pl/")
            .addConverterFactory(JspoonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

}