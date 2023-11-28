package com.nextsolutions.keyysafe.labels.ui.EditLabelsScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.common.util.IdentifierGenerator
import com.nextsolutions.keyysafe.db.entities.Label
import com.nextsolutions.keyysafe.entry.domain.model.EntryDataField
import com.nextsolutions.keyysafe.global_components.CustomTextField
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange


@Composable
fun EditLabelDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onEditDone: () -> Unit,
    titleTextValue: String,
    onTitleTextValueChange: (String) -> Unit
) {


    if (isVisible){
        Dialog(
            onDismissRequest = {
                onDismiss()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
                    .background(KeySafeTheme.colors.dialogBgColor)
                    .padding(KeySafeTheme.spaces.mediumLarge)
            ) {

                CustomTextField(
                    value = titleTextValue,
                    onValueChange = { onTitleTextValueChange(it) },
                    label = stringResource(R.string.label_name),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )


                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Text(
                        modifier = Modifier.clickable {
                            onDismiss()
                        },
                        text = stringResource(R.string.cancel).uppercase(),
                        color = Orange,
                    )

                    Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                    Text(
                        modifier = Modifier.clickable {
                            onEditDone()
                        },
                        text = stringResource(R.string.done).uppercase(),
                        color = Green,
                    )

                }

            }
        }
    }

}