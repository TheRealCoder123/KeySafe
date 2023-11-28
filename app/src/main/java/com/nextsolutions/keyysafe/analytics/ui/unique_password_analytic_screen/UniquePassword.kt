package com.nextsolutions.keyysafe.analytics.ui.unique_password_analytic_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun UniquePassword() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "Unique Password", color = KeySafeTheme.colors.text)
    }
}