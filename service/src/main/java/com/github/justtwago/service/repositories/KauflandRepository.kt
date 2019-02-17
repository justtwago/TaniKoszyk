package com.github.justtwago.service.repositories

import android.content.Context
import com.github.justtwago.service.common.Response
import com.github.justtwago.service.common.createRetrofit
import com.github.justtwago.service.common.executeSafely
import com.github.justtwago.service.model.kaufland.KauflandProductPage
import com.github.justtwago.service.services.KauflandService

const val KAUFLAND_BASE_URL = "https://www.kaufland.pl/"

interface KauflandRepository {
    suspend fun getProducts(searchQuery: String, page: Int): Response<KauflandProductPage>
}

class KauflandRepositoryImpl(private val context: Context) : KauflandRepository {
    override suspend fun getProducts(searchQuery: String, page: Int): Response<KauflandProductPage> {
        return createRetrofit(context, KAUFLAND_BASE_URL)
            .create(KauflandService::class.java)
            .getProducts(searchQuery = searchQuery, page = page - 1)
            .executeSafely()
    }
}