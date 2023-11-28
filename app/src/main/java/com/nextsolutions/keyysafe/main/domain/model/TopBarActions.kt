package com.nextsolutions.keyysafe.main.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class TopBarActions<ACTION_TYPE>(
    val icon: ImageVector,
    val contentDescription: String,
    val type: ACTION_TYPE
)
