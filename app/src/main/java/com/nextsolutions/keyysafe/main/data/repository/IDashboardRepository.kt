package com.nextsolutions.keyysafe.main.data.repository

import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker
import com.nextsolutions.keyysafe.db.database.DataAccessObject
import com.nextsolutions.keyysafe.db.entities.Entry
import com.nextsolutions.keyysafe.entry.domain.enums.EntryDataFieldType
import com.nextsolutions.keyysafe.main.domain.repository.DashboardEntryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IDashboardRepository @Inject constructor(
    private val dao: DataAccessObject,
) : DashboardEntryRepository {


    override suspend fun getEntries(): Flow<List<Entry>> {
        return dao.getEntries()
    }

    override suspend fun getEntriesByLabel(labelId: Int): Flow<List<Entry>> {
        return dao.getEntriesByLabel(labelId)
    }

    override suspend fun getRecentEntries(): Flow<List<Entry>> {
        return dao.getRecentEntries(20)
    }

    override suspend fun getRecentEntriesByLabel(labelId: Int): Flow<List<Entry>> {
        return dao.getRecentEntriesByLabel(25, labelId)
    }

    override fun getAllOrderedEntries(): Flow<List<Entry>> {
        return dao.getAllOrderedEntries()
    }


    override suspend fun getEntriesByStrength(
        entries: List<Entry>,
        passwordStrength: PasswordChecker.PasswordStrength,
    ) : List<Entry> {

        val passwordChecker = PasswordChecker()
        val allEntries = mutableListOf<Entry>()


        for (entry in entries) {
            val passwordFields = entry.fields.filter {
                it.type == EntryDataFieldType.Password && it.value.isNotEmpty()
            }

            for (field in passwordFields) {
                val passwordCheckResult = passwordChecker.checkPasswordStrength(field.value)
                if (passwordCheckResult == passwordStrength) {
                    allEntries.add(entry)
                    break
                }
            }
        }

        return allEntries
    }

    override fun getArchivedEntriesWithLimit(limit: Int) : Flow<List<Entry>> {
        return dao.getArchivedEntriesWithLimit(limit)
    }

    override fun getArchivedEntriesWithLimitByLabel(limit: Int, labelId: Int) : Flow<List<Entry>> {
        return dao.getArchivedEntriesWithLimitByLabel(limit, labelId)
    }

    override fun getTrashedEntriesWithLimit(limit: Int): Flow<List<Entry>> {
        return dao.getTrashedEntriesWithLimit(limit)
    }

    override fun getTrashedEntriesWithLimitByLabel(limit: Int, labelId: Int): Flow<List<Entry>> {
        return dao.getTrashedEntriesWithLimitByLabel(limit, labelId)
    }

    override suspend fun moveToTrash(trashed: Boolean, entryId: String) {
        dao.moveEntryToTrash(trashed, entryId)
    }

    override suspend fun archiveEntry(archived: Boolean, entryId: String) {
        dao.archiveEntry(archived, entryId)
    }

    override suspend fun getAllEntriesInTrash(): Flow<List<Entry>> {
        return dao.getTrashedEntries()
    }
}
