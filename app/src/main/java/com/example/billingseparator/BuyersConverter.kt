package com.example.billingseparator

import androidx.room.TypeConverter

class BuyersConverter {

    @TypeConverter
    fun fromBuyers(buyers: LongArray): String {
        return buyers.joinToString(", ")
    }

    @TypeConverter
    fun toBuyers(data: String): LongArray {
        return data.split(", ").map { it.toLong() }.toLongArray()
    }

}