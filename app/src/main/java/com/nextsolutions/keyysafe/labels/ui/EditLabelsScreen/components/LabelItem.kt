package com.nextsolutions.keyysafe.labels.ui.EditLabelsScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Label
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.db.entities.Label
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange

@Composable
fun LabelItem(
    label: Label,
    onClick: (Label) -> Unit = {},
    isSelected: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(KeySafeTheme.spaces.mediumLarge)
            .clickable {
                onClick(label)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row {
            Icon(
                imageVector = Icons.Default.Label,
                contentDescription = label.title,
                tint = KeySafeTheme.colors.iconTint
            )

            Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

            Text(
                text = label.title,
                color = KeySafeTheme.colors.text
            )
        }

        if (isSelected){
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = stringResource(R.string.selected_label),
                tint = Orange
            )
        }

    }
}

@Preview
@Composable
fun LabelItemPrev() {
    LabelItem(label = Label(1,"Google Accounts", false), isSelected = true)
}