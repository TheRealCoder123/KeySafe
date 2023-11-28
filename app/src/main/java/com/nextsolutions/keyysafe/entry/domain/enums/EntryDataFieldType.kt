package com.nextsolutions.keyysafe.entry.domain.enums

enum class EntryDataFieldType {
    Account, Password, Website, Username,
    Email, Note, Custom
}

fun getTypesAsList() : List<EntryDataFieldType> {
    return listOf(
        EntryDataFieldType.Account,
        EntryDataFieldType.Password,
        EntryDataFieldType.Website,
        EntryDataFieldType.Username,
        EntryDataFieldType.Email,
        EntryDataFieldType.Note,
        EntryDataFieldType.Custom,
    )
}