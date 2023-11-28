package com.nextsolutions.keyysafe.trash.domain.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

data class WarningData(
    val isVisible: Boolean = false,
    val title: String = "",
    val subTitle: String = "",
    val icon: ImageVector = Icons.Default.Warning
)
