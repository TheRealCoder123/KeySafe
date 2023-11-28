package com.nextsolutions.keyysafe.entry.domain.repository

import com.nextsolutions.keyysafe.db.entities.Entry
import kotlinx.coroutines.flow.Flow

interface CreateEditRepository {
    suspend fun insertEntry(entry: Entry)
    fun getEntry(id: String) : Flow<Entry>
    suspend fun getEntryWithoutFlow(id: String) : Entry
    suspend fun saveEntry(entry: Entry)
    suspend fun moveToTrash(trashed: Boolean, entryId: String)
    suspend fun getAllEntriesInTrash(): Flow<List<Entry>>
    suspend fun archiveEntry(archived: Boolean, entryId: String)
    suspend fun askForAuth(askForAuth: Boolean, entryId: String)
}