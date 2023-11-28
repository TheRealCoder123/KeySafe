package com.nextsolutions.keyysafe.global_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextsolutions.keyysafe.trash.domain.models.WarningData
import com.nextsolutions.keyysafe.ui.theme.Black
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange

@Composable
fun WarningComponent(
    modifier: Modifier = Modifier,
    data: WarningData,
) {



    val animateSize by animateDpAsState(
        targetValue = if (data.isVisible) 50.dp else 0.dp,
        label = "size_anim",
        animationSpec = tween(1000)
    )


    AnimatedVisibility(
        modifier = Modifier.fillMaxWidth(),
        visible = data.isVisible
    ) {
        Row(
            modifier = modifier
                .background(KeySafeTheme.colors.searchTFBackground)
                .padding(KeySafeTheme.spaces.mediumLarge)
                .fillMaxWidth()
                .height(animateSize),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = data.title,
                    color = KeySafeTheme.colors.text,
                    fontSize = 20.sp
                )
                Text(
                    text = data.subTitle,
                    color = Color.Gray,
                )
            }

            Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))


            Icon(
                imageVector = data.icon,
                contentDescription = data.title,
                tint = KeySafeTheme.colors.iconTint
            )


        }
    }
    
}