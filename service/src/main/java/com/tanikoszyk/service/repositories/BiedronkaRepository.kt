package com.tanikoszyk.service.repositories

import android.content.Context
import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.common.createRetrofit
import com.tanikoszyk.service.common.executeSafely
import com.tanikoszyk.service.model.data.biedronka.BiedronkaProduct
import com.tanikoszyk.service.model.data.biedronka.BiedronkaProductIdPage
import com.tanikoszyk.service.services.BiedronkaService

const val BIEDRONKA_BASE_URL = "http://www.biedronka.pl/"

interface BiedronkaRepository {
    suspend fun getProducts(searchQuery: String, page: Int): Response<BiedronkaProductIdPage>

    suspend fun getProduct(id: String): Response<BiedronkaProduct>
}

class BiedronkaRepositoryImpl(private val context: Context) : BiedronkaRepository {

    override suspend fun getProducts(searchQuery: String, page: Int): Response<BiedronkaProductIdPage> {
        return createRetrofit(context, BIEDRONKA_BASE_URL)
            .create(BiedronkaService::class.java)
            .getProducts(searchQuery = searchQuery, page = page)
            .executeSafely()
    }

    override suspend fun getProduct(id: String): Response<BiedronkaProduct> {
        return createRetrofit(context, BIEDRONKA_BASE_URL)
            .create(BiedronkaService::class.java)
            .getProduct(id = id)
            .executeSafely()
    }
}