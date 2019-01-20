package com.github.justtwago.service.repositories

import android.content.Context
import com.github.justtwago.service.common.Response
import com.github.justtwago.service.common.createRetrofit
import com.github.justtwago.service.common.executeSafely
import com.github.justtwago.service.model.tesco.TescoProductPage
import com.github.justtwago.service.services.TescoService

const val TESCO_BASE_URL = "https://ezakupy.tesco.pl/"
const val TESCO_PAGE_SIZE = 24

interface TescoRepository {
    suspend fun getProducts(searchQuery: String, page: Int): Response<TescoProductPage>
}

class TescoRepositoryImpl(private val context: Context) : TescoRepository {
    override suspend fun getProducts(searchQuery: String, page: Int): Response<TescoProductPage> {
        return createRetrofit(context, TESCO_BASE_URL)
            .create(TescoService::class.java)
            .getProducts(searchQuery = searchQuery, page = page)
            .executeSafely()
    }
}