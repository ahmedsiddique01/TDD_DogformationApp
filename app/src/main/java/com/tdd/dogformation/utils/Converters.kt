package com.tdd.dogformation.utils

import androidx.room.TypeConverter
import com.tdd.dogformation.utils.Constants.COMMA

class Converters {
    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(COMMA).map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = COMMA)
    }
}