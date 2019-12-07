package com.tanikoszyk.usecases.usecases.market

import com.fanmountain.domain.ProductPage
import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.model.mappers.mapToDomain
import com.tanikoszyk.service.repositories.AuchanRepository
import com.tanikoszyk.usecases.requests.MarketPageRequest
import com.tanikoszyk.usecases.requests.Result
import com.tanikoszyk.usecases.usecases.base.AsyncUseCase

class GetAuchanProductPageUseCase(
    private val auchanRepository: AuchanRepository
) : AsyncUseCase<MarketPageRequest, Result<ProductPage>> {

    override suspend fun execute(request: MarketPageRequest): Result<ProductPage> {
        val response = auchanRepository.getProducts(request.searchQuery, request.page, request.sortType)
        return when (response) {
            is Response.Success.WithBody -> {
                val product = response.body.mapToDomain()
                return Result.Success(product)
            }
            else -> Result.Failure()
        }
    }
}