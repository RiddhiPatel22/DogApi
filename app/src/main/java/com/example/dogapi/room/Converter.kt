package com.example.dogapi.room

import androidx.room.TypeConverter

class Converter {
    @TypeConverter
    fun fromListToString(list: List<String>) : String{
        return list.joinToString(",")
    }

    @TypeConverter
    fun fromStringToList(value: String) : List<String>{
        return value.split(",").filter { it.isNotEmpty() }
    }
}