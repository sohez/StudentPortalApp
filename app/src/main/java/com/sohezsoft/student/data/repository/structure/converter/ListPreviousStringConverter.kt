package com.sohezsoft.student.data.repository.structure.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sohezsoft.student.data.repository.structure.exam.Previous

class ListPreviousStringConverter {
    @TypeConverter
    fun encode(source: List<Previous>?): String {
        return if (source == null) {
            ""
        } else {
            val gson = Gson()
            return gson.toJson(source)
        }
    }

    @TypeConverter
    fun decode(source: String?): List<Previous>? {
        return if (source == null) {
            null
        } else {
            val gson = Gson()
            val type = object : TypeToken<List<Previous>>() {}
            return gson.fromJson(source, type)
        }
    }
}