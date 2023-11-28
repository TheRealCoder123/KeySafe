package com.nextsolutions.keyysafe.main.data.mapper

import com.nextsolutions.keyysafe.db.entities.Entry
import com.nextsolutions.keyysafe.entry.domain.enums.EntryDataFieldType
import com.nextsolutions.keyysafe.main.domain.model.DashboardEntry

fun Entry.toDashboardEntry() : DashboardEntry {
    val subTitle = fields.find { it.type == EntryDataFieldType.Account }?.value ?: ""
    return DashboardEntry(
        id,
        title,
        subTitle,
        timeStamp,
        color,
        askForAuth
    )
}