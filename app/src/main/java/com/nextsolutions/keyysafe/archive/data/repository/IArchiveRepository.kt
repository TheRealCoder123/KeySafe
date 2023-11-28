package com.nextsolutions.keyysafe.archive.data.repository

import com.nextsolutions.keyysafe.archive.domain.repository.ArchiveRepository
import com.nextsolutions.keyysafe.db.database.DataAccessObject
import com.nextsolutions.keyysafe.db.entities.Entry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IArchiveRepository @Inject constructor(
    private val dao: DataAccessObject
) : ArchiveRepository {
    override fun getArchivedEntries(): Flow<List<Entry>> {
        return dao.getArchivedEntries()
    }

    override fun getAllEntries(): Flow<List<Entry>> {
        return dao.getAllEntriesNotInTrash()
    }
}