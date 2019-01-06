package com.github.justtwago.tanikoszyk.services.tesco

import com.github.justtwago.tanikoszyk.model.tesco.TescoProductPage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface TescoService {
    @GET("groceries/pl-PL/search")
    fun getProducts(@Query("query") searchQuery: String, @Query("page") page: Int = 1): Call<TescoProductPage>
}