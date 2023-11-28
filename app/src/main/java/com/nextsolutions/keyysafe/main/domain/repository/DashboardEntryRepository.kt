package com.nextsolutions.keyysafe.main.domain.repository

import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker
import com.nextsolutions.keyysafe.db.entities.Entry
import kotlinx.coroutines.flow.Flow

interface DashboardEntryRepository {
    suspend fun getEntries() : Flow<List<Entry>>
    suspend fun getEntriesByLabel(labelId: Int) : Flow<List<Entry>>
    suspend fun getRecentEntries(): Flow<List<Entry>>
    suspend fun getRecentEntriesByLabel(labelId: Int): Flow<List<Entry>>
    fun getAllOrderedEntries(): Flow<List<Entry>>


    suspend fun getEntriesByStrength(
        entries: List<Entry>,
        passwordStrength: PasswordChecker.PasswordStrength,
    ) : List<Entry>

    fun getArchivedEntriesWithLimit(limit: Int = 10) : Flow<List<Entry>>
    fun getArchivedEntriesWithLimitByLabel(limit: Int = 10, labelId: Int) : Flow<List<Entry>>

    fun getTrashedEntriesWithLimit(limit: Int = 10) : Flow<List<Entry>>
    fun getTrashedEntriesWithLimitByLabel(limit: Int = 10, labelId: Int) : Flow<List<Entry>>

    suspend fun moveToTrash(trashed: Boolean, entryId: String)
    suspend fun archiveEntry(archived: Boolean, entryId: String)

    suspend fun getAllEntriesInTrash(): Flow<List<Entry>>



}