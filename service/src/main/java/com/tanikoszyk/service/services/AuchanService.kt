package com.tanikoszyk.service.services

import com.tanikoszyk.service.model.data.auchan.AuchanProductPage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface AuchanService {
    @GET("search/dynamic")
    fun getProducts(
            @Query("q") searchQuery: String,
            @Query("page") page: Int,
            @Query("sort") sortType: String
    ): Call<AuchanProductPage>
}