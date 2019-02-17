package com.github.justtwago.service.repositories

import android.content.Context
import com.github.justtwago.service.common.Response
import com.github.justtwago.service.common.createRetrofit
import com.github.justtwago.service.common.executeSafely
import com.github.justtwago.service.model.biedronka.BiedronkaProduct
import com.github.justtwago.service.model.biedronka.BiedronkaProductIdPage
import com.github.justtwago.service.services.BiedronkaService

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