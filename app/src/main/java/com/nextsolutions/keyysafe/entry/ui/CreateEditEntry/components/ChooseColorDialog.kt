package com.nextsolutions.keyysafe.entry.ui.CreateEditEntry.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange
import com.nextsolutions.keyysafe.ui.theme.entryColors

@Composable
fun ChooseColorDialog(
    isVisible: Boolean,
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit,
    selectedColor: Int
) {
    if (isVisible){
        Dialog(
            onDismissRequest = { onDismiss()}
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
                    .background(KeySafeTheme.colors.dialogBgColor)
                    .padding(KeySafeTheme.spaces.mediumLarge),
                columns = GridCells.Adaptive(70.dp)
            ){

                items(entryColors()){
                    Box(modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .padding(KeySafeTheme.spaces.medium)
                    ){

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(it)
                                .then(
                                    if (selectedColor == it.toArgb()){
                                        Modifier.border(2.dp, Green, CircleShape)
                                    }else{
                                        Modifier
                                    }
                                )
                                .clickable {
                                    onColorSelected(it)
                                }
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun ChooseColorDialogPrev() {
    ChooseColorDialog(
        true,
        onColorSelected = {},
        onDismiss = {},
        selectedColor = entryColors()[0].toArgb()
    )
}