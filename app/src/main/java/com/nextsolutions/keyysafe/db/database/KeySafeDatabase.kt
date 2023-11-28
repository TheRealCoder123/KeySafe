package com.nextsolutions.keyysafe.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nextsolutions.keyysafe.db.converters.EntryFieldsConverter
import com.nextsolutions.keyysafe.db.converters.EntryFilesConverter
import com.nextsolutions.keyysafe.db.entities.Entry
import com.nextsolutions.keyysafe.db.entities.Label

@Database(
    entities = [Entry::class, Label::class],
    version = 6
)
@TypeConverters(
    EntryFieldsConverter::class,
    EntryFilesConverter::class
)
abstract class KeySafeDatabase: RoomDatabase() {
    abstract val dao: DataAccessObject
    companion object {
        const val DATABASE_NAME = "key_safe_db"
    }
}