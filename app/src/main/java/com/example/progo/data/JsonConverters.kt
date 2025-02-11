package com.example.progo.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonConverters {
    @TypeConverter
    fun fromFloatListToJson(list: List<Float>): String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToFloatList(json: String): List<Float>{
        val type = object : TypeToken<List<Float>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromIntListToJson(list: List<Int>): String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToIntList(json: String): List<Int>{
        val type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(json, type)
    }
}