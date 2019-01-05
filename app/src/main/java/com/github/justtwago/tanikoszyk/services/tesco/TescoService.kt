package com.github.justtwago.tanikoszyk.services.tesco

import com.github.justtwago.tanikoszyk.model.tesco.TescoProductPage
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface TescoService {
    @GET("groceries/pl-PL/search")
    fun getProducts(@Query("query") searchQuery: String, @Query("page") page: String = "1"): Single<TescoProductPage>
}