package com.nextsolutions.keyysafe.settings.domain.models

import com.nextsolutions.keyysafe.common.util.AppTheme

data class SettingsState(
    val theme: AppTheme = AppTheme.System
)
