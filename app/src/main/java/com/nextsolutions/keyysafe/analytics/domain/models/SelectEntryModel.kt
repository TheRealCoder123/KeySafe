package com.nextsolutions.keyysafe.analytics.domain.models

import com.nextsolutions.keyysafe.db.entities.Entry

data class SelectEntryModel(
    val id: String,
    val title: String,
    val color: Int
)


fun Entry.toSelectEntryModel() : SelectEntryModel {
    return SelectEntryModel(id, title, color)
}
