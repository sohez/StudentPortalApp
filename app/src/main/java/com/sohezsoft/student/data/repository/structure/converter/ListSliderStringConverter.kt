package com.sohezsoft.student.data.repository.structure.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sohezsoft.student.data.repository.structure.personal.Slider

class ListSliderStringConverter
{
    @TypeConverter
    fun encode(source: List<Slider>?): String {
        return if (source == null) {
            ""
        } else {
            val gson = Gson()
            return gson.toJson(source)
        }
    }

    @TypeConverter
    fun decode(source: String?): List<Slider>? {
        return if (source == null) {
            null
        } else {
            val gson = Gson()
            val type = object : TypeToken<List<Slider>>() {}
            return gson.fromJson(source, type)
        }
    }
}