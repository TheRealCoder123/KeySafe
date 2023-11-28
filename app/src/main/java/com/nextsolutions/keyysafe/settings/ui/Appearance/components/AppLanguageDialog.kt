package com.nextsolutions.keyysafe.settings.ui.Appearance.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
fun AppLanguage(
    isVisible: Boolean,
    selectedLanguage: String,
    onLanguageClicked: (String) -> Unit,
    onDismiss: () -> Unit
) {

    if (isVisible){

        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
                    .background(KeySafeTheme.colors.dialogBgColor)
                    .padding(KeySafeTheme.spaces.mediumLarge)
            ) {

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            onLanguageClicked("en")
                        },
                        text = stringResource(R.string.english),
                        color = KeySafeTheme.colors.text
                    )

                    if (selectedLanguage == "en"){
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.selected_language),
                            tint = KeySafeTheme.colors.iconTint
                        )
                    }



                }

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            onLanguageClicked("mk")
                        },
                        text = stringResource(R.string.macedonian),
                        color = KeySafeTheme.colors.text
                    )

                    if (selectedLanguage == "mk"){
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.selected_language),
                            tint = KeySafeTheme.colors.iconTint
                        )
                    }

                }



                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            onLanguageClicked("de")
                        },
                        text = stringResource(R.string.german),
                        color = KeySafeTheme.colors.text
                    )

                    if (selectedLanguage == "de"){
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.selected_language),
                            tint = KeySafeTheme.colors.iconTint
                        )
                    }

                }
                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            onLanguageClicked("pt")
                        },
                        text = stringResource(R.string.portuguese),
                        color = KeySafeTheme.colors.text
                    )

                    if (selectedLanguage == "pt"){
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.selected_language),
                            tint = KeySafeTheme.colors.iconTint
                        )
                    }

                }

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            onLanguageClicked("es")
                        },
                        text = stringResource(R.string.spanish),
                        color = KeySafeTheme.colors.text
                    )

                    if (selectedLanguage == "es"){
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.selected_language),
                            tint = KeySafeTheme.colors.iconTint
                        )
                    }

                }

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            onLanguageClicked("sr")
                        },
                        text = stringResource(R.string.serbian),
                        color = KeySafeTheme.colors.text
                    )

                    if (selectedLanguage == "sr"){
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.selected_language),
                            tint = KeySafeTheme.colors.iconTint
                        )
                    }

                }




                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

            }
        }
    }


}