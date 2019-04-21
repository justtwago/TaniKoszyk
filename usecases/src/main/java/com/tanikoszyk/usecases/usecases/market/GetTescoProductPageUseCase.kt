package com.tanikoszyk.usecases.usecases.market

import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.repositories.AuchanRepository
import com.tanikoszyk.service.repositories.TescoRepository
import com.tanikoszyk.usecases.model.Result
import com.tanikoszyk.usecases.model.market.MarketPageRequest
import com.tanikoszyk.usecases.model.market.common.ProductPage
import com.tanikoszyk.usecases.model.market.common.mapToDomain
import com.tanikoszyk.usecases.model.market.common.mapToService
import com.tanikoszyk.usecases.usecases.base.AsyncUseCase
import com.tanikoszyk.usecases.usecases.realtimedb.CheckIfProductExistsUseCase

class GetTescoProductPageUseCase(
    private val tescoRepository: TescoRepository,
    private val checkIfProductExistsUseCase: CheckIfProductExistsUseCase
) :
    AsyncUseCase<MarketPageRequest, Result<ProductPage>> {

    override suspend fun execute(request: MarketPageRequest): Result<ProductPage> {
        val response = tescoRepository.getProducts(request.searchQuery, request.page, request.sortType.mapToService())
        return when (response) {
            is Response.Success.WithBody -> {
                val product = response.body.mapToDomain()
                return Result.Success(
                    product.copy(
                        products = product.products.map {
                            it.copy(isSelected = checkIfProductExistsUseCase.execute(it))
                        }
                    )
                )
            }
            else -> Result.Failure()
        }
    }
}