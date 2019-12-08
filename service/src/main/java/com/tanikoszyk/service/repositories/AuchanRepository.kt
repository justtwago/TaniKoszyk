package com.tanikoszyk.service.repositories

import com.tanikoszyk.domain.ProductPage
import com.tanikoszyk.domain.Result
import com.tanikoszyk.domain.SortType
import com.tanikoszyk.service.common.execute
import com.tanikoszyk.service.model.mappers.mapToDomain
import com.tanikoszyk.service.services.AuchanService

const val AUCHAN_BASE_URL = "https://www.auchandirect.pl/"

interface AuchanRepository {
    suspend fun getProducts(searchQuery: String, page: Int, sortType: SortType = SortType.TARGET): Result<ProductPage>
}

internal class AuchanRepositoryImpl(private val service: AuchanService) : AuchanRepository {

    override suspend fun getProducts(searchQuery: String, page: Int, sortType: SortType): Result<ProductPage> {
        return service.getProducts(
            searchQuery = searchQuery,
            page = page - 1,
            sortType = sortType.toAuchanQuery()
        ).execute { it.mapToDomain() }
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