package com.nextsolutions.keyysafe.trash.ui.TrashScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun TrashEntryDialog(
    isVisible: Boolean,
    onDelete: () -> Unit,
    onRemoveFromTrash: () -> Unit,
    onDismiss: () -> Unit
) {

    if (isVisible){

        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
                    .background(KeySafeTheme.colors.dialogBgColor)
                    .padding(KeySafeTheme.spaces.mediumLarge),
            ) {

                Row(
                    modifier = Modifier.clickable { onRemoveFromTrash() }
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.RestoreFromTrash,
                        contentDescription = stringResource(R.string.remove_from_trash),
                        tint = KeySafeTheme.colors.iconTint
                    )

                    Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                    Text(
                        text = stringResource(R.string.remove_from_trash),
                        color = KeySafeTheme.colors.text
                    )
                }

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))


                Row(
                    modifier = Modifier.clickable { onDelete() }
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteForever,
                        contentDescription = stringResource(R.string.delete_entry),
                        tint = KeySafeTheme.colors.iconTint
                    )

                    Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                    Text(
                        text = stringResource(R.string.delete_entry),
                        color = KeySafeTheme.colors.text
                    )
                }


            }
        }

    }

}