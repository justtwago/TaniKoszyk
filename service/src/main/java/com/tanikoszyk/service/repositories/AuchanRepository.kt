package com.tanikoszyk.service.repositories

import android.content.Context
import com.fanmountain.domain.SortType
import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.common.createRetrofit
import com.tanikoszyk.service.common.executeSafely
import com.tanikoszyk.service.model.data.auchan.AuchanProductPage
import com.tanikoszyk.service.services.AuchanService

const val AUCHAN_BASE_URL = "https://www.auchandirect.pl/"

interface AuchanRepository {
    suspend fun getProducts(
        searchQuery: String,
        page: Int,
        sortType: SortType = SortType.TARGET
    ): Response<AuchanProductPage>
}

class AuchanRepositoryImpl(private val context: Context) : AuchanRepository {

    override suspend fun getProducts(searchQuery: String, page: Int, sortType: SortType): Response<AuchanProductPage> {
        return createRetrofit(context, AUCHAN_BASE_URL)
            .create(AuchanService::class.java)
            .getProducts(searchQuery = searchQuery, page = page - 1, sortType = sortType.toAuchanQuery())
            .executeSafely()
    }

    private fun SortType.toAuchanQuery(): String {
        return when (this) {
            SortType.TARGET -> "relevance-asc"
            SortType.ALPHABETICAL_ASCEND -> "name-asc"
            SortType.ALPHABETICAL_DESCEND -> "name-desc"
            SortType.PRICE_ASCEND -> "price-asc"
            SortType.PRICE_DESCEND -> "price-desc"
        }
    }
}