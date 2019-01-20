package com.github.justtwago.service.repositories

import android.content.Context
import com.github.justtwago.service.common.Response
import com.github.justtwago.service.common.createRetrofit
import com.github.justtwago.service.common.executeSafely
import com.github.justtwago.service.model.auchan.AuchanProductPage
import com.github.justtwago.service.services.AuchanService

const val AUCHAN_BASE_URL = "https://www.auchandirect.pl/"
const val AUCHAN_PAGE_SIZE = 50

interface AuchanRepository {
    suspend fun getProducts(searchQuery: String, page: Int): Response<AuchanProductPage>
}

class AuchanRepositoryImpl(private val context: Context) : AuchanRepository {

    override suspend fun getProducts(searchQuery: String, page: Int): Response<AuchanProductPage> {
        return createRetrofit(context, AUCHAN_BASE_URL)
            .create(AuchanService::class.java)
            .getProducts(searchQuery = searchQuery, page = page - 1)
            .executeSafely()
    }
}