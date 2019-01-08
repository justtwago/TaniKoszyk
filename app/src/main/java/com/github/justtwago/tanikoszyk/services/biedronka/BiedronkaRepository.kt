package com.github.justtwago.tanikoszyk.services.biedronka

import android.content.Context
import com.github.justtwago.tanikoszyk.common.extensions.Response
import com.github.justtwago.tanikoszyk.common.extensions.createRetrofit
import com.github.justtwago.tanikoszyk.common.extensions.executeSafely
import com.github.justtwago.tanikoszyk.model.biedronka.BiedronkaProduct
import com.github.justtwago.tanikoszyk.model.biedronka.BiedronkaProductIdPage

const val BIEDRONKA_BASE_URL = "http://www.biedronka.pl/"
const val BIEDRONKA_PAGE_SIZE = 20

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