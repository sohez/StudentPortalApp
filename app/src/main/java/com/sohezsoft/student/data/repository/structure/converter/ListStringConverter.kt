package com.sohezsoft.student.data.repository.structure.converter

import androidx.room.TypeConverter

class ListStringConverter{

    @TypeConverter
     fun encode(source: List<String>?): String {
        return if (source == null) {
            ""
        } else {
            source.joinToString(separator = ",")
        }
    }

    @TypeConverter
     fun decode(source: String?): List<String>? {
        return if (source == null) {
            null
        } else {
            source.split(",")
        }
    }
}
