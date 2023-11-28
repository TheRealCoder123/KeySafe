package com.nextsolutions.keyysafe.entry.ui.CreateEditEntry.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.common.util.IdentifierGenerator
import com.nextsolutions.keyysafe.entry.domain.enums.EntryDataFieldType
import com.nextsolutions.keyysafe.entry.domain.model.EntryDataField
import com.nextsolutions.keyysafe.global_components.CustomTextField
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange

@Composable
fun AddNewFieldDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onDone: (EntryDataField) -> Unit,
    fields: List<EntryDataField>
) {
    if (isVisible){

        val spaceBetweenItems = KeySafeTheme.spaces.mediumLarge

        var type by rememberSaveable {
            mutableStateOf(EntryDataFieldType.Email)
        }

        var value by rememberSaveable {
            mutableStateOf("")
        }

        var title by rememberSaveable {
            mutableStateOf(type.name.replace('_', ' '))
        }

        var titleTextState by rememberSaveable {
            mutableStateOf("")
        }

        var isSelectFieldTypeDialogVisible by rememberSaveable {
            mutableStateOf(false)
        }

        SelectAFieldDialog(
            fields = fields,
            isVisible = isSelectFieldTypeDialogVisible,
            onDismiss = { isSelectFieldTypeDialogVisible = false },
            onSelected = {
                type = it
                if (it == EntryDataFieldType.Custom){
                    title = "Label"
                    titleTextState = ""
                }else {
                    titleTextState = it.name
                }
                isSelectFieldTypeDialogVisible = false
            }
        )

        Dialog(
            onDismissRequest = { onDismiss() }
        ) {

            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
                    .background(KeySafeTheme.colors.dialogBgColor)
                    .padding(spaceBetweenItems),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

               item {
                   Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))
                   Text(
                       modifier = Modifier.fillMaxWidth(),
                       text = stringResource(R.string.add_new_field),
                       color = KeySafeTheme.colors.text,
                       textAlign = TextAlign.Center
                   )
                   Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))


                   Box(
                       modifier = Modifier
                           .fillMaxWidth()
                           .background(KeySafeTheme.colors.dialogBgColor)
                           .border(1.dp, Orange, RoundedCornerShape(KeySafeTheme.spaces.medium))
                           .padding(KeySafeTheme.spaces.mediumLarge)
                           .clickable {
                               isSelectFieldTypeDialogVisible = true
                           }
                   ){
                       Text(
                           text = type.name.replace('_', ' '),
                           color = KeySafeTheme.colors.text
                       )
                   }


                   if (type == EntryDataFieldType.Custom){
                       Spacer(modifier = Modifier.height(spaceBetweenItems))
                       CustomTextField(
                           value = titleTextState,
                           onValueChange = { titleTextState = it },
                           label = title,
                           keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                           singleLine = type != EntryDataFieldType.Note
                       )
                   }

                   Spacer(modifier = Modifier.height(spaceBetweenItems))

                   CustomTextField(
                       value = value,
                       onValueChange = { value = it },
                       label = stringResource(R.string.value),
                       keyboardOptions = KeyboardOptions(
                           keyboardType = when(type){
                               EntryDataFieldType.Account,
                               EntryDataFieldType.Website,
                               EntryDataFieldType.Username,
                               EntryDataFieldType.Note,
                               EntryDataFieldType.Password -> KeyboardType.Password
                               EntryDataFieldType.Email -> KeyboardType.Email
                               EntryDataFieldType.Custom -> KeyboardType.Text
                           }
                       ),
                       singleLine = type != EntryDataFieldType.Note
                   )

                   Spacer(modifier = Modifier.height(spaceBetweenItems))

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

                       Spacer(modifier = Modifier.width(spaceBetweenItems))

                       Text(
                           modifier = Modifier.clickable {
                               val dataField = EntryDataField(
                                   IdentifierGenerator.generateUUID(),
                                   type,
                                   titleTextState,
                                   value
                               )
                               onDone(dataField)
                           },
                           text = stringResource(R.string.done).uppercase(),
                           color = Green,
                       )

                   }

               }

            }

        }


    }
}