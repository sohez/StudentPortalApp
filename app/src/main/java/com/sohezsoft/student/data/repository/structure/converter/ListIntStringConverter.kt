package com.sohezsoft.student.data.repository.structure.converter

import androidx.room.TypeConverter

class ListIntStringConverter {
    @TypeConverter
    fun encode(source: List<Long>?): String {
        return if (source == null) {
            ""
        } else {
            source.joinToString(separator = ",")
        }
    }

    @TypeConverter
    fun decode(source: String?): List<Long>? {
        return if (source == null) {
            null
        } else {
            source.split(",").map { it.toLong() }
        }
    }
}