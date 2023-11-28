package com.nextsolutions.keyysafe.main.ui.dashboard.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun DrawerHeader() {

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = stringResource(id = R.string.app_name),
            Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.width(KeySafeTheme.spaces.medium))

        Text(
            text = stringResource(id = R.string.app_name),
            color = KeySafeTheme.colors.text,
            fontSize = 25.sp,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            )
        )
    }

    Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Gray))

}