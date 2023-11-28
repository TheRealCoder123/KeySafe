package com.nextsolutions.keyysafe.entry.domain.model

data class CreateEntry(
    val entryId: String,
    val title: String,
    val labelId: Int,
    val color: Int
)
