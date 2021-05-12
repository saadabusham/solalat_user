package com.raantech.solalat.user.data.db.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raantech.solalat.user.data.models.ServiceCategory
import com.raantech.solalat.user.data.models.media.Media
import java.lang.reflect.Type

class CategoryConverter {

    @TypeConverter
    fun storedStringToObject(value: String?): ServiceCategory? {
        val type: Type = object : TypeToken<ServiceCategory?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun objectToStoredString(data: ServiceCategory?): String? {
        val gson = Gson()
        return gson.toJson(data)
    }
}