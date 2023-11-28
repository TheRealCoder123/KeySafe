package com.nextsolutions.keyysafe.settings.ui.SettingsScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextsolutions.keyysafe.settings.domain.enums.SettingsItemType
import com.nextsolutions.keyysafe.settings.domain.models.SettingsItem
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    data: SettingsItem,
    onClick: (SettingsItemType) -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(data.type)
            },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = data.icon,
            contentDescription = data.title,
            tint = KeySafeTheme.colors.iconTint
        )

        Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

        Text(
            text = data.title,
            color = KeySafeTheme.colors.text,
            fontSize = 20.sp
        )

    }

}