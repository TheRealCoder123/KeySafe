package com.nextsolutions.keyysafe.archive.domain.repository

import com.nextsolutions.keyysafe.db.entities.Entry
import kotlinx.coroutines.flow.Flow

interface ArchiveRepository {

    fun getArchivedEntries() : Flow<List<Entry>>

    fun getAllEntries() : Flow<List<Entry>>

}