package com.sohezsoft.student.data.repository.structure.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sohezsoft.student.data.repository.structure.presente.Holiday


class ListHolidayStringConverter {
    @TypeConverter
    fun encode(source: List<Holiday>?): String {
        return if (source == null) {
            ""
        } else {
            val gson = Gson()
            return gson.toJson(source)
        }
    }

    @TypeConverter
    fun decode(source: String?): List<Holiday>? {
        return if (source == null) {
            null
        } else {
            val gson = Gson()
            val type = object : TypeToken<List<Holiday>>() {}
            return gson.fromJson(source, type)
        }
    }
}