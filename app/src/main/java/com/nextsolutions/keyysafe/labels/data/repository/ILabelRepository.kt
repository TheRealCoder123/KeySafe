package com.nextsolutions.keyysafe.labels.data.repository

import com.nextsolutions.keyysafe.db.database.DataAccessObject
import com.nextsolutions.keyysafe.db.entities.Label
import com.nextsolutions.keyysafe.labels.domain.repository.LabelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ILabelRepository @Inject constructor(
    private val dao: DataAccessObject
): LabelRepository {
    override suspend fun insertLabel(label: Label) {
        dao.insertLabel(label)
    }

    override fun getLabels(): Flow<List<Label>> {
        return dao.getLabels()
    }

    override suspend fun deleteLabel(labelId: Int) {
        dao.deleteLabel(labelId)
    }

    override suspend fun deleteConnectionsToLabel(deleteLabelId: Int) {
        dao.deleteConnectionsToLabel(deleteLabelId)
    }

    override suspend fun updateLabelTitle(newTitle: String, labelId: Int) {
        dao.updateLabelTitle(newTitle, labelId)
    }
}