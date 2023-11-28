package com.nextsolutions.keyysafe.entry.data.repository

import com.nextsolutions.keyysafe.db.database.DataAccessObject
import com.nextsolutions.keyysafe.db.entities.Entry
import com.nextsolutions.keyysafe.entry.domain.repository.CreateEditRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ICreateEditRepository @Inject constructor(
    private val dao: DataAccessObject
) : CreateEditRepository {
    override suspend fun insertEntry(entry: Entry) {
        dao.insertEntry(entry)
    }

    override fun getEntry(id: String): Flow<Entry> {
        return dao.getEntry(id)
    }

    override suspend fun getEntryWithoutFlow(id: String): Entry {
        return dao.getEntryWithoutFlow(id)
    }

    override suspend fun saveEntry(entry: Entry) {
        dao.saveEntry(
            entry.id,
            entry.title,
            entry.fields,
            entry.files,
            entry.labelId,
            entry.color,
            entry.trashed,
            entry.archived,
            entry.timeStamp
        )
    }

    override suspend fun moveToTrash(trashed: Boolean, entryId: String) {
        dao.moveEntryToTrash(trashed, entryId)
    }

    override suspend fun getAllEntriesInTrash(): Flow<List<Entry>> {
        return dao.getTrashedEntries()
    }

    override suspend fun archiveEntry(archived: Boolean, entryId: String) {
        dao.archiveEntry(archived, entryId)
    }

    override suspend fun askForAuth(askForAuth: Boolean, entryId: String) {
        dao.changeAskForAuth(askForAuth, entryId)
    }
}