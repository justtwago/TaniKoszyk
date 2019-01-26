package com.github.justtwago.service.services

import com.github.justtwago.service.model.tesco.TescoProductPage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface TescoService {
    @GET("groceries/pl-PL/search")
    fun getProducts(
            @Query("query") searchQuery: String,
            @Query("page") page: Int = 1,
            @Query("sortBy") sortType: String
    ): Call<TescoProductPage>
}