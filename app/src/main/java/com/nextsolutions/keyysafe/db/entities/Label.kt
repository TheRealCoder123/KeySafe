package com.nextsolutions.keyysafe.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "label")
data class Label(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val pinned: Boolean
)
