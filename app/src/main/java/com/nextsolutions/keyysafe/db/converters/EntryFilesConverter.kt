package com.nextsolutions.keyysafe.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nextsolutions.keyysafe.entry.domain.model.EntryFiles

class EntryFilesConverter {
    @TypeConverter
    fun fromEntryFile(files: List<EntryFiles>): String? {
        val gson = Gson()
        return gson.toJson(files)
    }

    @TypeConverter
    fun toEntryFile(files: String?): List<EntryFiles> {
        val gson = Gson()
        val type = object : TypeToken<List<EntryFiles>>() {}.type
        return gson.fromJson(files, type)
    }
}