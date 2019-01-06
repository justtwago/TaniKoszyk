package com.github.justtwago.tanikoszyk.services.kaufland

import android.content.Context
import com.github.justtwago.tanikoszyk.common.extensions.Response
import com.github.justtwago.tanikoszyk.model.kaufland.KauflandProductPage
import com.github.justtwago.tanikoszyk.common.extensions.createRetrofit
import com.github.justtwago.tanikoszyk.common.extensions.executeSafely

const val KAUFLAND_BASE_URL = "https://www.kaufland.pl/"
const val KAUFLAND_PAGE_SIZE = 60

interface KauflandRepository {
    suspend fun getProducts(searchQuery: String, page: Int): Response<KauflandProductPage>
}

class KauflandRepositoryImpl(private val context: Context) : KauflandRepository {
    override suspend fun getProducts(searchQuery: String, page: Int): Response<KauflandProductPage> {
        return createRetrofit(context, KAUFLAND_BASE_URL)
            .create(KauflandService::class.java)
            .getProducts(searchQuery = searchQuery, page = page - 1)
            .executeSafely()
    }
}