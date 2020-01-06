package com.tanikoszyk.storage.di

import android.content.Context
import androidx.room.Room
import com.tanikoszyk.storage.database.CartDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Provides
    @Singleton
    internal fun cartDatabase(context: Context): CartDatabase =
        Room.databaseBuilder(context, CartDatabase::class.java, CartDatabase.NAME).build()

    @Provides
    internal fun cartStorage(cartDatabase: CartDatabase): CartStorage = LocalCartStorage(cartDatabase.cartDao())
}