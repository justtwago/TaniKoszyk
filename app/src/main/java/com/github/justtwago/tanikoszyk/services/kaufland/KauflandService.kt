package com.github.justtwago.tanikoszyk.services.kaufland

import com.github.justtwago.tanikoszyk.model.kaufland.KauflandProductPage
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface KauflandService {
    @GET("wyszukiwarka.specialOfferSearch=1.pageIndex={page}.html")
    fun getProducts(@Path("page") page: String = "0", @Query("q") searchQuery: String): Single<KauflandProductPage>
}