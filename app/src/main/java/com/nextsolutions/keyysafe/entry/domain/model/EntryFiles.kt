package com.nextsolutions.keyysafe.entry.domain.model

import com.nextsolutions.keyysafe.entry.domain.enums.EntryFileType

data class EntryFiles(
    val id: String,
    val fileType: EntryFileType,
    val fileUri: String
)