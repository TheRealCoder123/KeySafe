package com.nextsolutions.keyysafe.main.ui.dashboard_2_0

import com.nextsolutions.keyysafe.db.entities.Label
import com.nextsolutions.keyysafe.main.domain.model.DashboardEntry

data class DashboardState(
    val entries: List<DashboardEntry> = emptyList(),
    val labels: List<Label> = emptyList(),
    val selectedLabelId: Int = -1
)
