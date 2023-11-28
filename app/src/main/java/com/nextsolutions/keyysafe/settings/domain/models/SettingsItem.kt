package com.nextsolutions.keyysafe.settings.domain.models

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Security
import androidx.compose.ui.graphics.vector.ImageVector
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.settings.domain.enums.SettingsItemType

data class SettingsItem(
    val icon: ImageVector,
    val title: String,
    val type: SettingsItemType
){

    companion object {
        fun getSettingsItems(context: Context) : List<SettingsItem> {
            return listOf(
                SettingsItem(
                    Icons.Default.DarkMode,
                    context.getString(R.string.appearance),
                    SettingsItemType.Appearance
                ),
                SettingsItem(
                    Icons.Default.Security,
                    context.getString(R.string.security),
                    SettingsItemType.Security
                ),
//                SettingsItem(
//                    Icons.Default.Dataset,
//                    context.getString(R.string.database),
//                    SettingsItemType.Database
//                ),
                SettingsItem(
                    Icons.Default.Article,
                    context.getString(R.string.about),
                    SettingsItemType.About
                ),
                SettingsItem(
                    Icons.Default.Policy,
                    context.getString(R.string.privacy_policy),
                    SettingsItemType.Privacy_Policy
                ),
            )
        }
    }

}

