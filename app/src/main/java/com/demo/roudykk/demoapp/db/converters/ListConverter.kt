package com.demo.roudykk.demoapp.db.converters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class ListConverter {

    @TypeConverter
    fun stringToObjectList(data: String?): List<Any> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Any>>() {

        }.type

        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun objectListToString(objects: List<Any>): String {
        return Gson().toJson(objects)
    }

}