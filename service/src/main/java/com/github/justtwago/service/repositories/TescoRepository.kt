package com.github.justtwago.service.repositories

import android.content.Context
import com.github.justtwago.service.common.Response
import com.github.justtwago.service.common.createRetrofit
import com.github.justtwago.service.common.executeSafely
import com.github.justtwago.service.model.domain.SortTypeService
import com.github.justtwago.service.model.tesco.TescoProductPage
import com.github.justtwago.service.services.TescoService

const val TESCO_BASE_URL = "https://ezakupy.tesco.pl/"

interface TescoRepository {
    suspend fun getProducts(searchQuery: String, page: Int, sortType: SortTypeService = SortTypeService.TARGET): Response<TescoProductPage>
}

class TescoRepositoryImpl(private val context: Context) : TescoRepository {
    override suspend fun getProducts(searchQuery: String, page: Int, sortType: SortTypeService): Response<TescoProductPage> {
        return createRetrofit(context, TESCO_BASE_URL)
            .create(TescoService::class.java)
            .getProducts(searchQuery = searchQuery, page = page, sortType = sortType.toTescoQuery())
            .executeSafely()
    }

    private fun SortTypeService.toTescoQuery(): String {
        return when (this) {
            SortTypeService.TARGET -> ""
            SortTypeService.ALPHABETICAL_ASCEND -> "titleAscending"
            SortTypeService.ALPHABETICAL_DESCEND -> "titleDescending"
            SortTypeService.PRICE_ASCEND -> "priceAscending"
            SortTypeService.PRICE_DESCEND -> "priceDescending"
        }
    }
}