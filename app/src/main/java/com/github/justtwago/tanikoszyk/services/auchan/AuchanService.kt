package com.github.justtwago.tanikoszyk.services.auchan

import com.github.justtwago.tanikoszyk.model.auchan.AuchanProductPage
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface AuchanService {
    @GET("search")
    fun getProducts(@Query("text") searchQuery: String): Single<AuchanProductPage>
}