package com.tanikoszyk.usecases.usecases.market

import com.nhaarman.mockitokotlin2.whenever
import com.tanikoszyk.service.common.Response
import com.tanikoszyk.service.repositories.KauflandRepository
import com.tanikoszyk.usecases.requests.Result
import com.tanikoszyk.usecases.sample.kauflandProductPage
import com.tanikoszyk.usecases.sample.marketPageRequest
import com.tanikoszyk.usecases.sample.productPageKaufland
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetKauflandProductPageUseCaseTest {

    @Mock lateinit var kauflandRepository: KauflandRepository
    @InjectMocks lateinit var useCase: GetKauflandProductPageUseCase

    @Test
    fun `return kaufland product result page successfully`() = runBlocking {
        whenever(kauflandRepository.getProducts(marketPageRequest.searchQuery, marketPageRequest.page))
            .thenReturn(Response.Success.WithBody(kauflandProductPage))

        val result = useCase.execute(marketPageRequest)

        assertEquals(
            (result as Result.Success).result.marketProducts.first().toString(),
            productPageKaufland.marketProducts.first().toString()
        )
        assertEquals(result.result.pageCount, productPageKaufland.pageCount)
    }

    @Test
    fun `return failure when get kaufland product page`() = runBlocking {
        whenever(kauflandRepository.getProducts(marketPageRequest.searchQuery, marketPageRequest.page))
            .thenReturn(Response.Failure(Throwable()))

        val result = useCase.execute(marketPageRequest)

        assertTrue(result is Result.Failure)
    }
}