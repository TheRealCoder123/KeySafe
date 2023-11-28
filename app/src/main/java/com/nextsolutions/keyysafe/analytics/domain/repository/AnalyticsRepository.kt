package com.nextsolutions.keyysafe.analytics.domain.repository

import com.nextsolutions.keyysafe.db.entities.Entry
import kotlinx.coroutines.flow.Flow

interface AnalyticsRepository {

    fun getEntries() : Flow<List<Entry>>
    fun getEntryById(entryId: String) : Flow<Entry>

}