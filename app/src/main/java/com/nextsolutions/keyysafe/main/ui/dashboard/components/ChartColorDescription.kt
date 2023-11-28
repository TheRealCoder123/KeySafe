package com.nextsolutions.keyysafe.main.ui.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun ChartColorDescription(
    modifier: Modifier = Modifier,
    color: Color,
    text: String
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(KeySafeTheme.spaces.small))
                .background(color)
        )

        Spacer(modifier = Modifier.width(KeySafeTheme.spaces.medium))

        Text(
            text = text,
            color = KeySafeTheme.colors.text
        )

    }

}