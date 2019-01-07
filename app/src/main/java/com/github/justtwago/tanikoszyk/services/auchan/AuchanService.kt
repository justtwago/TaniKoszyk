package com.github.justtwago.tanikoszyk.services.auchan

import com.github.justtwago.tanikoszyk.model.auchan.AuchanProductPage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface AuchanService {
    @GET("search/dynamic")
    fun getProducts(@Query("q") searchQuery: String, @Query("page") page: Int): Call<AuchanProductPage>
}