package com.tanikoszyk.usecases.usecases.market

import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.repositories.AuchanRepository
import com.tanikoszyk.usecases.model.Result
import com.tanikoszyk.usecases.model.market.MarketPageRequest
import com.tanikoszyk.usecases.model.market.common.ProductPage
import com.tanikoszyk.usecases.model.market.common.mapToDomain
import com.tanikoszyk.usecases.model.market.common.mapToService
import com.tanikoszyk.usecases.usecases.base.AsyncUseCase


class GetAuchanProductPageUseCase(private val auchanRepository: AuchanRepository) : AsyncUseCase<MarketPageRequest, Result<ProductPage>> {
    override suspend fun execute(request: MarketPageRequest): Result<ProductPage> {
        val response = auchanRepository.getProducts(request.searchQuery, request.page, request.sortType.mapToService())
        return when (response) {
            is Response.Success.WithBody -> Result.Success(response.body.mapToDomain())
            else -> Result.Failure()
        }
    }
}