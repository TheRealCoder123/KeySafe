package com.nextsolutions.keyysafe.app.graphs

sealed class SettingsNavigation(val route: String){
    object SettingsScreen: SetupNavigation("settings_screen")
}
