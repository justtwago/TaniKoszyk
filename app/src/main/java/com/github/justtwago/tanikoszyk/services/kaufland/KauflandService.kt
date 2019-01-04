package com.github.justtwago.tanikoszyk.services.kaufland

import com.github.justtwago.tanikoszyk.model.kaufland.KauflandProductPage
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface KauflandService {
    @GET("wyszukiwarka.html")
    fun getProducts(@Query("q") searchQuery: String): Single<KauflandProductPage>
}