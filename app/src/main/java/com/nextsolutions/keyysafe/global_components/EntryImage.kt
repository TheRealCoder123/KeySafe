package com.nextsolutions.keyysafe.global_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nextsolutions.keyysafe.common.util.ExtractLetters
import com.nextsolutions.keyysafe.ui.theme.Orange

@Composable
fun EntryImage(
    modifier : Modifier = Modifier,
    entryTitle: String,
    entryColor: Int,
    size: Dp = 40.dp,
    onClick: () -> Unit = {}
) {

    val titleWithTwoLetters = ExtractLetters.extractFirstTwoLetters(entryTitle)

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Color(entryColor))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ){
        Text(
            text = titleWithTwoLetters,
            color = Color.White
        )
    }

}

@Preview
@Composable
fun EntryImagePreview() {
    EntryImage(entryTitle ="Google Accounts", entryColor = Orange.toArgb())
}