package com.tanikoszyk.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tanikoszyk.storage.model.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
@TypeConverters(Converters::class)
internal abstract class CartDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object {
        const val NAME = "cart-database"
    }
}