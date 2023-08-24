package com.nextsolutions.keyysafe.auth.ui.AuthScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun AuthScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = stringResource(id = R.string.app_name)
                )

                Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                Text(
                    text = stringResource(id = R.string.app_name),
                    color = KeySafeTheme.colors.text,
                    fontSize = 25.sp
                )

            }


        }
    }
}