package com.github.justtwago.usecases.usecases.market

import com.github.justtwago.service.common.Response
import com.github.justtwago.service.repositories.AuchanRepository
import com.github.justtwago.usecases.model.Result
import com.github.justtwago.usecases.model.market.MarketPageRequest
import com.github.justtwago.usecases.model.market.common.ProductPage
import com.github.justtwago.usecases.model.market.common.mapToDomain
import com.github.justtwago.usecases.model.market.common.mapToService
import com.github.justtwago.usecases.usecases.base.AsyncUseCase


class GetAuchanProductPageUseCase(private val auchanRepository: AuchanRepository) : AsyncUseCase<MarketPageRequest, Result<ProductPage>> {
    override suspend fun execute(request: MarketPageRequest): Result<ProductPage> {
        val response = auchanRepository.getProducts(request.searchQuery, request.page, request.sortType.mapToService())
        return when (response) {
            is Response.Success.WithBody -> Result.Success(response.body.mapToDomain())
            else -> Result.Failure()
        }
    }
}