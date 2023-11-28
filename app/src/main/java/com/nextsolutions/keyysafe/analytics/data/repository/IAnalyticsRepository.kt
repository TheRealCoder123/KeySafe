package com.nextsolutions.keyysafe.analytics.data.repository

import com.nextsolutions.keyysafe.analytics.domain.repository.AnalyticsRepository
import com.nextsolutions.keyysafe.db.database.DataAccessObject
import com.nextsolutions.keyysafe.db.entities.Entry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IAnalyticsRepository @Inject constructor(
    private val dao: DataAccessObject
) : AnalyticsRepository {
    override fun getEntries(): Flow<List<Entry>> {
        return dao.getAllEntries()
    }

    override fun getEntryById(entryId: String): Flow<Entry> {
        return dao.getEntry(entryId)
    }
}