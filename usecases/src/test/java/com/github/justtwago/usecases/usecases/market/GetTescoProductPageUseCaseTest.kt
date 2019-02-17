package com.github.justtwago.usecases.usecases.market

import com.github.justtwago.service.common.Response
import com.github.justtwago.service.model.domain.SortTypeService
import com.github.justtwago.service.repositories.TescoRepository
import com.github.justtwago.usecases.model.Result
import com.github.justtwago.usecases.sample.marketPageRequest
import com.github.justtwago.usecases.sample.productPageTesco
import com.github.justtwago.usecases.sample.tescoProductPage
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTescoProductPageUseCaseTest {
    @Mock lateinit var tescoRepository: TescoRepository
    @InjectMocks lateinit var useCase: GetTescoProductPageUseCase

    @Test
    fun `return tesco product result page successfully`() = runBlocking {
        whenever(tescoRepository.getProducts(marketPageRequest.searchQuery, marketPageRequest.page, SortTypeService.TARGET))
            .thenReturn(Response.Success.WithBody(tescoProductPage))

        val result = useCase.execute(marketPageRequest)

        assertEquals((result as Result.Success).result.products.first().toString(), productPageTesco.products.first().toString())
        assertEquals(result.result.pageCount, productPageTesco.pageCount)
    }

    @Test
    fun `return failure when get tesco product page`() = runBlocking {
        whenever(tescoRepository.getProducts(marketPageRequest.searchQuery, marketPageRequest.page))
            .thenReturn(Response.Failure(Throwable()))

        val result = useCase.execute(marketPageRequest)

        assertTrue(result is Result.Failure)
    }
}