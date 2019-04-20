package com.tanikoszyk.service.repositories

import android.content.Context
import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.common.createRetrofit
import com.tanikoszyk.service.common.executeSafely
import com.tanikoszyk.service.model.kaufland.KauflandProductPage
import com.tanikoszyk.service.services.KauflandService

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