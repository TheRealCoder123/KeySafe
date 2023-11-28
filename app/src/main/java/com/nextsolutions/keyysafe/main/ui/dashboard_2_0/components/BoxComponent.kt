package com.nextsolutions.keyysafe.main.ui.dashboard_2_0.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun BoxComponent(
    onClick: () -> Unit,
    label: String,
    icon: ImageVector
) {

    Column(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
            .background(KeySafeTheme.colors.drawerBgColor)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(KeySafeTheme.colors.selected)
                .padding(KeySafeTheme.spaces.medium),
            contentAlignment = Alignment.Center
        ){

            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White
            )

        }

        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))


        Text(
            text = label,
            color = KeySafeTheme.colors.text
        )


    }


}


@Preview
@Composable
fun BoxComponentPrev() {
    BoxComponent(onClick = {}, label = "Archive", icon = Icons.Default.Archive)
}