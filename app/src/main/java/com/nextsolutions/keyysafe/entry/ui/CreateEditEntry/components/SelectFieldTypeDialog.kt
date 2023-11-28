package com.nextsolutions.keyysafe.entry.ui.CreateEditEntry.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.entry.domain.enums.EntryDataFieldType
import com.nextsolutions.keyysafe.entry.domain.enums.getTypesAsList
import com.nextsolutions.keyysafe.entry.domain.model.EntryDataField
import com.nextsolutions.keyysafe.global_components.GreenButton
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun SelectAFieldDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onSelected: (EntryDataFieldType) -> Unit,
    fields: List<EntryDataField>
) {
    if (isVisible){

        val spaceBetweenItems = KeySafeTheme.spaces.mediumLarge
        val context = LocalContext.current

        Dialog(onDismissRequest = { onDismiss() }) {
            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
                    .background(KeySafeTheme.colors.dialogBgColor)
                    .padding(spaceBetweenItems),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){

                item {
                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.select_a_field_type),
                        color = KeySafeTheme.colors.text,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))
                }


                items(getTypesAsList()){

                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                    GreenButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            val hasPasswordField = fields.any { addedField -> addedField.type == EntryDataFieldType.Password }
                            if (!hasPasswordField) {
                                onSelected(it)
                            } else {
                                if (it == EntryDataFieldType.Password) {
                                    Toast.makeText(
                                        context,
                                        "You already have a password field.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    onSelected(it)
                                }
                            }

                        },
                        label = when(it){
                            EntryDataFieldType.Account -> stringResource(id = R.string.account)
                            EntryDataFieldType.Password -> stringResource(id = R.string.password)
                            EntryDataFieldType.Website -> stringResource(id = R.string.website)
                            EntryDataFieldType.Username -> stringResource(id = R.string.username)
                            EntryDataFieldType.Email -> stringResource(id = R.string.email)
                            EntryDataFieldType.Note -> stringResource(id = R.string.note)
                            EntryDataFieldType.Custom -> stringResource(id = R.string.custom)
                        })
                }
            }
        }

    }
}