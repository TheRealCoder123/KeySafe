package com.nextsolutions.keyysafe.trash.domain.repository

import com.nextsolutions.keyysafe.db.entities.Entry
import kotlinx.coroutines.flow.Flow

interface TrashRepository {
    suspend fun getTrashedEntries() : Flow<List<Entry>>
    suspend fun getAllEntriesNotArchived() : Flow<List<Entry>>

    suspend fun deleteEntry(entryId: String)
    suspend fun removeFromTrash(entryId: String)

}