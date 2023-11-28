package com.nextsolutions.keyysafe.settings.ui.Appearance.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.common.util.AppTheme
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun AppThemeDialog(
    isVisible: Boolean,
    onThemeClicked: (AppTheme) -> Unit,
    onDismiss: () -> Unit
) {

    if (isVisible){
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
                    .background(KeySafeTheme.colors.dialogBgColor)
                    .padding(KeySafeTheme.spaces.mediumLarge)
            ) {

                Row(
                    modifier = Modifier.clickable { onThemeClicked(AppTheme.Light) }
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LightMode,
                        contentDescription = stringResource(R.string.light_theme),
                        tint = KeySafeTheme.colors.iconTint
                    )

                    Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                    Text(
                        text = stringResource(R.string.light_theme),
                        color = KeySafeTheme.colors.text
                    )
                }

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))


                Row(
                    modifier = Modifier.clickable { onThemeClicked(AppTheme.Dark) }
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DarkMode,
                        contentDescription = stringResource(R.string.dark_theme),
                        tint = KeySafeTheme.colors.iconTint
                    )

                    Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                    Text(
                        text = stringResource(R.string.dark_theme),
                        color = KeySafeTheme.colors.text
                    )
                }

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                Row(
                    modifier = Modifier.clickable { onThemeClicked(AppTheme.System) }
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoMode,
                        contentDescription = stringResource(R.string.system_theme),
                        tint = KeySafeTheme.colors.iconTint
                    )

                    Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                    Text(
                        text = stringResource(R.string.system_theme),
                        color = KeySafeTheme.colors.text
                    )
                }

            }
        }
    }


}