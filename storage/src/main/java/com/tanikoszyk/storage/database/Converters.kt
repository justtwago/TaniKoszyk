package com.tanikoszyk.storage.database

import androidx.room.TypeConverter
import com.tanikoszyk.storage.model.MarketEntity

internal class Converters {

    companion object {
        @TypeConverter @JvmStatic
        fun marketToString(market: MarketEntity): String = market.name

        @TypeConverter @JvmStatic
        fun stringToMarket(name: String): MarketEntity = MarketEntity.valueOf(name)
    }
}