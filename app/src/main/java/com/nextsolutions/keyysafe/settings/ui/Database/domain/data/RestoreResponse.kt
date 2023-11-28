package com.nextsolutions.keyysafe.settings.ui.Database.domain.data

data class RestoreResponse(
    val success: Boolean = false,
    val error: String = "",
    val backupData: BackupData = BackupData(emptyList(), emptyList())
)
