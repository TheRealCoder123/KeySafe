package com.nextsolutions.keyysafe.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nextsolutions.keyysafe.common.util.IdentifierGenerator
import com.nextsolutions.keyysafe.entry.domain.enums.EntryDataFieldType
import com.nextsolutions.keyysafe.entry.domain.model.EntryDataField
import com.nextsolutions.keyysafe.entry.domain.model.EntryFiles

@Entity(tableName = "entry")
data class Entry(
    @PrimaryKey
    val id: String,
    val title: String,
    val fields: List<EntryDataField>,
    val files: List<EntryFiles>,
    val labelId: Int,
    val color: Int,
    val trashed: Boolean,
    val archived: Boolean,
    val timeStamp: Long,
    val askForAuth: Boolean
){
    companion object {
        val requiredFields = listOf(
            EntryDataField(
                IdentifierGenerator.generateUUID(),
                EntryDataFieldType.Account,
                "Account",
                ""
            ),
            EntryDataField(
                IdentifierGenerator.generateUUID(),
                EntryDataFieldType.Username,
                "Username",
                ""
            ),
            EntryDataField(
                IdentifierGenerator.generateUUID(),
                EntryDataFieldType.Password,
                "Password",
                ""
            )
        )
    }
}
