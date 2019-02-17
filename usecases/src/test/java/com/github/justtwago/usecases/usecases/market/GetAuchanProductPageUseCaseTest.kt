package com.github.justtwago.usecases.usecases.market

import com.github.justtwago.service.common.Response
import com.github.justtwago.service.model.domain.SortTypeService
import com.github.justtwago.service.repositories.AuchanRepository
import com.github.justtwago.usecases.model.Result
import com.github.justtwago.usecases.sample.auchanProductPage
import com.github.justtwago.usecases.sample.marketPageRequest
import com.github.justtwago.usecases.sample.productPageAuchan
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAuchanProductPageUseCaseTest {
    @Mock lateinit var auchanRepository: AuchanRepository
    @InjectMocks lateinit var useCase: GetAuchanProductPageUseCase

    @Test
    fun `return auchan product result page successfully`() = runBlocking {
        whenever(auchanRepository.getProducts(marketPageRequest.searchQuery, marketPageRequest.page, SortTypeService.TARGET))
            .thenReturn(Response.Success.WithBody(auchanProductPage))

        val result = useCase.execute(marketPageRequest)

        assertEquals((result as Result.Success).result.products.first().toString(), productPageAuchan.products.first().toString())
        assertEquals(result.result.pageCount, productPageAuchan.pageCount)
    }

    @Test
    fun `return failure when get auchan product page`() = runBlocking {
        whenever(auchanRepository.getProducts(marketPageRequest.searchQuery, marketPageRequest.page, SortTypeService.TARGET))
            .thenReturn(Response.Failure(Throwable()))

        val result = useCase.execute(marketPageRequest)

        assertTrue(result is Result.Failure)
    }
}