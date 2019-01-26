package com.github.justtwago.service.repositories

import android.content.Context
import com.github.justtwago.service.common.Response
import com.github.justtwago.service.common.createRetrofit
import com.github.justtwago.service.common.executeSafely
import com.github.justtwago.service.model.auchan.AuchanProductPage
import com.github.justtwago.service.model.domain.SortType
import com.github.justtwago.service.services.AuchanService

const val AUCHAN_BASE_URL = "https://www.auchandirect.pl/"
const val AUCHAN_PAGE_SIZE = 50

interface AuchanRepository {
    suspend fun getProducts(searchQuery: String, page: Int, sortType: SortType = SortType.TARGET): Response<AuchanProductPage>
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