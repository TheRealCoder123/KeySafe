package com.nextsolutions.keyysafe.global_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (newValue: String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions,
    singleLine: Boolean = false,
    trailingIcon: @Composable () -> Unit = {},
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = KeySafeTheme.colors.text,
            cursorColor = if (value.isEmpty()) Orange else Green,
            focusedBorderColor = if (value.isEmpty()) Orange else Green,
            unfocusedBorderColor = if (value.isEmpty()) Orange else Green
        ),
        keyboardOptions = keyboardOptions,
        label = {
            Text(
                text = label,
                color = KeySafeTheme.colors.text
            )
        },
        trailingIcon = {
            trailingIcon()
        },
        singleLine = singleLine
    )
}