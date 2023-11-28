package com.nextsolutions.keyysafe.analytics.ui.analytics_screen

import com.nextsolutions.keyysafe.db.entities.Entry
import com.nextsolutions.keyysafe.main.domain.model.DashboardEntry

data class AnalyticsScreenState(
    val entries: List<DashboardEntry> = emptyList(),
    val checkByEntry: Entry? = null
)
