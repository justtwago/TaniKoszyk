package com.tanikoszyk.storage.di

import com.tanikoszyk.domain.MarketProduct
import com.tanikoszyk.storage.database.CartDao
import com.tanikoszyk.storage.model.toDomain
import com.tanikoszyk.storage.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface CartStorage {

    fun subscribeCartProducts(): Flow<List<MarketProduct>>
    suspend fun saveProduct(marketProduct: MarketProduct)
    suspend fun removeProduct(productUrl: String)
}

internal class LocalCartStorage(private val cartDao: CartDao) : CartStorage {

    override fun subscribeCartProducts(): Flow<List<MarketProduct>> {
        return cartDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveProduct(marketProduct: MarketProduct) {
        cartDao.insert(marketProduct.toEntity())
    }

    override suspend fun removeProduct(productUrl: String) {
        cartDao.delete(productUrl)
    }

}