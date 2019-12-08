package com.tanikoszyk.service.services

import com.tanikoszyk.service.model.data.biedronka.BiedronkaProduct
import com.tanikoszyk.service.model.data.biedronka.BiedronkaProductIdPage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

const val BIEDRONKA_SINGLE_PRODUCT_REQUEST = "pl/product,id,"

internal interface BiedronkaService {

    @GET("pl/search,type,1,query,{query},page,{page}")
    fun getProducts(@Path("query") searchQuery: String, @Path("page") page: Int = 1): Call<BiedronkaProductIdPage>

    @GET("$BIEDRONKA_SINGLE_PRODUCT_REQUEST{id}")
    fun getProduct(@Path("id") id: String): Call<BiedronkaProduct>
}