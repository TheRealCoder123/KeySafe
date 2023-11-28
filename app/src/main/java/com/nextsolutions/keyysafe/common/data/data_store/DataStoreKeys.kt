package com.nextsolutions.keyysafe.common.data.data_store

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val AppTheme = stringPreferencesKey(name = "app_theme")
    val AppLanguage = stringPreferencesKey(name = "app_language")
    val AuthenticationScreen = booleanPreferencesKey(name = "auth_screen")
    val Notification = booleanPreferencesKey(name = "notification")
    val NavBarColor = booleanPreferencesKey(name = "nav_bar_color_bool")
    val AllowScreenShots = booleanPreferencesKey(name = "allow_screen_shots")
    val isAutoBackupOn = booleanPreferencesKey(name = "isAutoBackupOn")
}