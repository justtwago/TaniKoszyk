package com.tanikoszyk.usecases.usecases.market

import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.repositories.BiedronkaRepository
import com.tanikoszyk.usecases.model.Result
import com.tanikoszyk.usecases.sample.*
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetBiedronkaProductPageUseCaseTest {
    @Mock lateinit var biedronkaRepository: BiedronkaRepository
    @InjectMocks lateinit var useCase: GetBiedronkaProductPageUseCase

    @Test
    fun `return biedronka product result page successfully`() = runBlocking {
        whenever(biedronkaRepository.getProducts(marketPageRequest.searchQuery, marketPageRequest.page))
            .thenReturn(Response.Success.WithBody(biedronkaProductIdPage))

        whenever(biedronkaRepository.getProduct(biedronkaProductId))
            .thenReturn(Response.Success.WithBody(biedronkaProduct))

        val result = useCase.execute(marketPageRequest)

        assertEquals((result as Result.Success).result.products.first().toString(), productPageBiedronka.products.first().toString())
        assertEquals(result.result.pageCount, productPageBiedronka.pageCount)
    }

    @Test
    fun `return failure when get biedronka product page`() = runBlocking {
        whenever(biedronkaRepository.getProducts(marketPageRequest.searchQuery, marketPageRequest.page))
            .thenReturn(Response.Failure(Throwable()))

        val result = useCase.execute(marketPageRequest)

        assertTrue(result is Result.Failure)
    }

}