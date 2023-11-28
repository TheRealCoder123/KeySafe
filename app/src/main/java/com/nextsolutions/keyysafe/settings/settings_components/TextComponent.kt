package com.nextsolutions.keyysafe.settings.settings_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    onClick: () -> Unit = {}
) {

    Column(
        modifier = modifier.fillMaxWidth()
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = title,
            color = KeySafeTheme.colors.text,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.medium))

        Text(
            text = subTitle,
            color = Gray,
            fontSize = 14.sp
        )

    }

}