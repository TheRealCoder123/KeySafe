package com.nextsolutions.keyysafe.analytics.ui.password_diversity_checker_analtyic_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun PasswordDiversityChecker() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "Password Diversity Checker", color = KeySafeTheme.colors.text)
    }
}