package com.nextsolutions.keyysafe.settings.ui.Database.domain.data

import com.nextsolutions.keyysafe.db.entities.Entry
import com.nextsolutions.keyysafe.db.entities.Label

data class BackupData(
    val entries: List<Entry>,
    val labels: List<Label>
)


const val BACKUP_DATA_PASSWORD = "7sD#K&yPv9!wZ@1_KEY_SAFe"