package com.nextsolutions.keyysafe.entry.ui.CreateEditEntry.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.db.entities.Label
import com.nextsolutions.keyysafe.labels.ui.EditLabelsScreen.components.LabelItem
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun SetALabelDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    labels: List<Label>,
    currentLabelId: Int,
    onLabelClicked: (Label) -> Unit
) {
    if (isVisible){
        
        val spaceBetweenItems = KeySafeTheme.spaces.mediumLarge
        
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(spaceBetweenItems)
                    .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
                    .background(KeySafeTheme.colors.dialogBgColor)
            ) {
                item {
                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.all_labels),
                        color = KeySafeTheme.colors.text,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))
                }
                
                items(labels){
                    LabelItem(
                        label = it,
                        onClick = { label ->
                            onLabelClicked(label)
                        },
                        isSelected = currentLabelId == it.id
                    )
                }
                
            }
        }
        
    }
}