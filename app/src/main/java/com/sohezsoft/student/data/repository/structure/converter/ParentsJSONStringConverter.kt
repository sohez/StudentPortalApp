package com.sohezsoft.student.data.repository.structure.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sohezsoft.student.data.repository.structure.personal.Parents

class ParentsJSONStringConverter {
    @TypeConverter
    fun encode(source: Parents?): String {
        return if (source == null) {
            ""
        } else {
            val gson = Gson()
            return gson.toJson(source)
        }
    }

    @TypeConverter
    fun decode(source: String?): Parents? {
        return if (source == null) {
            null
        } else {
            val gson = Gson()
            return gson.fromJson(source, Parents::class.java)
        }
    }
}