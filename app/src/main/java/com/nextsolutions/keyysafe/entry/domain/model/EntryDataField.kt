package com.nextsolutions.keyysafe.entry.domain.model

import com.nextsolutions.keyysafe.entry.domain.enums.EntryDataFieldType

data class EntryDataField(
    val id: String,
    val type: EntryDataFieldType,
    val title: String,
    val value: String
)