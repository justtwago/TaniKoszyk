package com.github.justtwago.service.services

import com.github.justtwago.service.model.biedronka.BiedronkaProduct
import com.github.justtwago.service.model.biedronka.BiedronkaProductIdPage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

private const val USER_AGENT = "Mozilla/5.0 (Linux; <Android Version>; <Build Tag etc.>) AppleWebKit/<WebKit Rev> (KHTML, like Gecko) Chrome/<Chrome Rev> Mobile Safari/<WebKit Rev>"
interface BiedronkaService {

    @GET("pl/search,type,1,query,{query},page,{page}")
    fun getProducts(@Path("query") searchQuery: String, @Path("page") page: Int = 1): Call<BiedronkaProductIdPage>

    @GET("pl/product,id,{id}")
    fun getProduct(@Path("id") id: String): Call<BiedronkaProduct>
}