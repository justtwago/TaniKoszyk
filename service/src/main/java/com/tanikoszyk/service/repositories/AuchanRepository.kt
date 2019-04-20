package com.tanikoszyk.service.repositories

import android.content.Context
import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.common.createRetrofit
import com.tanikoszyk.service.common.executeSafely
import com.tanikoszyk.service.model.auchan.AuchanProductPage
import com.tanikoszyk.service.model.service.SortTypeService
import com.tanikoszyk.service.services.AuchanService

const val AUCHAN_BASE_URL = "https://www.auchandirect.pl/"

interface AuchanRepository {
    suspend fun getProducts(searchQuery: String, page: Int, sortType: SortTypeService = SortTypeService.TARGET): Response<AuchanProductPage>
}

class AuchanRepositoryImpl(private val context: Context) : AuchanRepository {

    override suspend fun getProducts(searchQuery: String, page: Int, sortType: SortTypeService): Response<AuchanProductPage> {
        return createRetrofit(context, AUCHAN_BASE_URL)
            .create(AuchanService::class.java)
            .getProducts(searchQuery = searchQuery, page = page - 1, sortType = sortType.toAuchanQuery())
            .executeSafely()
    }


    private fun SortTypeService.toAuchanQuery(): String {
        return when (this) {
            SortTypeService.TARGET -> "relevance-asc"
            SortTypeService.ALPHABETICAL_ASCEND -> "name-asc"
            SortTypeService.ALPHABETICAL_DESCEND -> "name-desc"
            SortTypeService.PRICE_ASCEND -> "price-asc"
            SortTypeService.PRICE_DESCEND -> "price-desc"
        }
    }
}