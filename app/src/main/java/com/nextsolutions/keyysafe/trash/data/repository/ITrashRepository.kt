package com.nextsolutions.keyysafe.trash.data.repository

import com.nextsolutions.keyysafe.db.database.DataAccessObject
import com.nextsolutions.keyysafe.db.entities.Entry
import com.nextsolutions.keyysafe.trash.domain.repository.TrashRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ITrashRepository @Inject constructor(
    private val dataAccessObject: DataAccessObject
) : TrashRepository {
    override suspend fun getTrashedEntries(): Flow<List<Entry>> {
        return dataAccessObject.getTrashedEntries()
    }

    override suspend fun getAllEntriesNotArchived(): Flow<List<Entry>> {
        return dataAccessObject.getAllEntriesNotArchived()
    }

    override suspend fun deleteEntry(entryId: String) {
        dataAccessObject.deleteEntry(entryId)
    }

    override suspend fun removeFromTrash(entryId: String) {
        dataAccessObject.removeFromTrash(entryId)
    }

}