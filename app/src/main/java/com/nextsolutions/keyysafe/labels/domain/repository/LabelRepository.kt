package com.nextsolutions.keyysafe.labels.domain.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nextsolutions.keyysafe.db.entities.Label
import kotlinx.coroutines.flow.Flow

interface LabelRepository {
    suspend fun insertLabel(label: Label)
    fun getLabels() : Flow<List<Label>>

    suspend fun deleteLabel(labelId: Int)
    suspend fun deleteConnectionsToLabel(deleteLabelId: Int)
    suspend fun updateLabelTitle(newTitle: String, labelId: Int)


}