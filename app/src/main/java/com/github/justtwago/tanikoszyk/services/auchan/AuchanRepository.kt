package com.github.justtwago.tanikoszyk.services.auchan

import android.content.Context
import com.github.justtwago.tanikoszyk.common.extensions.Response
import com.github.justtwago.tanikoszyk.model.auchan.AuchanProductPage
import com.github.justtwago.tanikoszyk.common.extensions.createRetrofit
import com.github.justtwago.tanikoszyk.common.extensions.executeSafely

const val AUCHAN_BASE_URL = "https://www.auchandirect.pl"
const val AUCHAN_PAGE_SIZE = 50

interface AuchanRepository {
    suspend fun getProducts(searchQuery: String, page: Int): Response<AuchanProductPage>
}

class AuchanRepositoryImpl(private val context: Context) : AuchanRepository {

    override suspend fun getProducts(searchQuery: String, page: Int): Response<AuchanProductPage> {
        return createRetrofit(context, "$AUCHAN_BASE_URL/")
            .create(AuchanService::class.java)
            .getProducts(searchQuery, page = page - 1)
            .executeSafely()
    }
}