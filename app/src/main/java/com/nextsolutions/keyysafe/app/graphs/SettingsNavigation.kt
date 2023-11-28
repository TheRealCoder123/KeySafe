package com.nextsolutions.keyysafe.app.graphs

import com.nextsolutions.keyysafe.common.util.ArgumentKeys

sealed class SettingsNavigation(val route: String){
    object SettingsScreen: SettingsNavigation("settings_screen")
    object Appearance: SettingsNavigation("appearance_screen")
    object Security: SettingsNavigation("security_screen")
    object Database: SettingsNavigation("database_screen")
    object About: SettingsNavigation("about_screen")
    object ChangeMasterPassword: SettingsNavigation("change_master_password_screen")
    object ViewFileDataScreen: SettingsNavigation("view_file_data_screen/{${ArgumentKeys.FILE_URI_TO_VIEW_FILE_DATA}}"){
        fun passData(uri: String): String{
            return this.route.replace(
                oldValue = "{${ArgumentKeys.FILE_URI_TO_VIEW_FILE_DATA}}",
                newValue = uri
            )
        }

    }
}
