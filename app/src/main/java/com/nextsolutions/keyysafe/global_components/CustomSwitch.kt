package com.nextsolutions.keyysafe.global_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nextsolutions.keyysafe.ui.theme.Black
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange

@Composable
fun CustomSwitch(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String = "",
    enabled: Boolean = true,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {},
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = KeySafeTheme.colors.text,
                fontSize = 20.sp
            )
            if (subtitle.isNotEmpty()){
                Text(
                    text = subtitle,
                    color = Gray,
                )
            }
        }

        Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

        Switch(
            checked = checked,
            onCheckedChange = {
                onCheckedChange(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Black,
                uncheckedThumbColor = Black,
                uncheckedTrackColor = Orange,
                checkedTrackColor = Green,
                checkedBorderColor = Color.Transparent,
                uncheckedBorderColor = Color.Transparent,
            ),
            enabled = enabled
        )
    }
}


@Preview
@Composable
fun CustomSwitchPrev() {
    CustomSwitch(
        title = "Move to trash",
        subtitle = "This entry is moved to trash where it can be deleted.",
        checked = false,
        onCheckedChange = {}
    )
}