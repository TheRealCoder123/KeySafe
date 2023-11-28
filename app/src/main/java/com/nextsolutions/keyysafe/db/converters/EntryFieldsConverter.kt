package com.nextsolutions.keyysafe.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nextsolutions.keyysafe.entry.domain.model.EntryDataField

class EntryFieldsConverter {

    @TypeConverter
    fun fromEntryField(fields: List<EntryDataField>): String? {
        val gson = Gson()
        return gson.toJson(fields)
    }

    @TypeConverter
    fun toEntryField(fields: String?): List<EntryDataField> {
        val gson = Gson()
        val type = object : TypeToken<List<EntryDataField>>() {}.type
        return gson.fromJson(fields, type)
    }

}