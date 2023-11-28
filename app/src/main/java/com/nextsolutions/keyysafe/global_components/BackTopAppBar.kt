package com.nextsolutions.keyysafe.global_components

import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.White

@Composable
fun BackTopAppBar(
    title: String,
    onBackPress: () -> Unit,
    action: @Composable () -> Unit = {},
    backgroundColor: Color = KeySafeTheme.colors.primary,
    onBackgroundColor: Color = White
) {

    TopAppBar(
        backgroundColor = backgroundColor,
        title = {
            Text(
                text = title,
                color = onBackgroundColor
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onBackPress() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = stringResource(id = R.string.back),
                    tint = onBackgroundColor
                )
            }
        },
        actions = {
            action()
        }
    )

}