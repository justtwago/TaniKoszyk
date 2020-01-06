package com.tanikoszyk.storage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.tanikoszyk.storage.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CartDao {

    @Query("SELECT * FROM ProductEntity")
    fun getAll(): Flow<List<ProductEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(marketProduct: ProductEntity)

    @Query("DELETE FROM productentity WHERE url = :productUrl")
    suspend fun delete(productUrl: String)
}