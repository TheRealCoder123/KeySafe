package com.nextsolutions.keyysafe.main.ui.password_generator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker
import com.nextsolutions.keyysafe.common.util.ArgumentKeys
import com.nextsolutions.keyysafe.global_components.BackTopAppBar
import com.nextsolutions.keyysafe.global_components.CustomSwitch
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange
import com.nextsolutions.keyysafe.ui.theme.Red
import com.nextsolutions.keyysafe.ui.theme.White

@Composable
fun PasswordGeneratorScreen(
    navController: NavController,
    viewModel: PasswordGeneratorViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


    LaunchedEffect(
        keys = arrayOf(
            viewModel.useDigits,
            viewModel.useSymbols,
            viewModel.useCapitalLetters,
            viewModel.sliderPosition
        )
    ){
        viewModel.generatePassword()
    }


    Scaffold(
        backgroundColor = KeySafeTheme.colors.background,
        topBar = {
            BackTopAppBar(
                title = stringResource(id = R.string.password_generator),
                onBackPress = {
                    navController.navigateUp()
                },
                action = {
                    IconButton(onClick = {
                        viewModel.generatePassword()
                    }) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = stringResource(R.string.re_generate),
                            tint = White
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (viewModel.shouldSendBackResult){
               Box(modifier = Modifier
                   .fillMaxWidth()
                   .padding(KeySafeTheme.spaces.mediumLarge), contentAlignment = Alignment.Center){
                   FloatingActionButton(
                       backgroundColor = Green,
                       onClick = {
                           navController.popBackStack()
                           navController.currentBackStackEntry
                               ?.savedStateHandle
                               ?.set(ArgumentKeys.PASS_GENERATED_PASSWORD_BACK, viewModel.generatedPasswordTextState)
                       }
                   ) {
                       Icon(
                           imageVector = Icons.Default.DoneAll,
                           contentDescription = stringResource(id = R.string.generated_password),
                       )
                   }
               }
            }
        }
    ){ paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = KeySafeTheme.spaces.mediumLarge)
        ) {


             item {
                 Column(
                     modifier = Modifier.fillMaxWidth()
                 ) {
                     Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                     Row(
                         modifier = Modifier.fillMaxWidth(),
                         verticalAlignment = Alignment.CenterVertically,
                         horizontalArrangement = Arrangement.SpaceBetween
                     ) {
                         Text(
                             modifier = Modifier.fillMaxWidth(),
                             text = stringResource(R.string.generated_password),
                             color = KeySafeTheme.colors.text,
                             textAlign = TextAlign.Start
                         )



                     }

                     Spacer(modifier = Modifier.height(KeySafeTheme.spaces.small))

                     Box(
                         modifier = Modifier
                             .fillMaxWidth()
                             .height(100.dp)
                             .background(
                                 color = KeySafeTheme.colors.searchTFBackground,
                                 shape = RoundedCornerShape(16.dp)
                             )
                             .clickable {
                                 val clipData = ClipData.newPlainText(
                                     context.getString(R.string.generated_password),
                                     viewModel.generatedPasswordTextState
                                 )
                                 clipboardManager.setPrimaryClip(clipData)
                                 Toast
                                     .makeText(
                                         context,
                                         context.getString(R.string.password_copied),
                                         Toast.LENGTH_SHORT
                                     )
                                     .show()
                             }
                     ) {
                         Text(
                             text = viewModel.generatedPasswordTextState,
                             color = KeySafeTheme.colors.text,
                             textAlign = TextAlign.Start,
                             modifier = Modifier.padding(16.dp),
                             fontSize = 20.sp
                         )
                     }

                     Spacer(modifier = Modifier.height(KeySafeTheme.spaces.small))

                     Text(
                         modifier = Modifier.fillMaxWidth(),
                         text = when(viewModel.passwordStrength){
                             PasswordChecker.PasswordStrength.STRONG -> stringResource(id = R.string.password_is_strong)
                             PasswordChecker.PasswordStrength.MEDIUM -> stringResource(id = R.string.password_is_medium)
                             PasswordChecker.PasswordStrength.WEAK -> stringResource(id = R.string.password_is_weak)
                         },
                         color = when(viewModel.passwordStrength){
                             PasswordChecker.PasswordStrength.STRONG -> Green
                             PasswordChecker.PasswordStrength.MEDIUM -> Orange
                             PasswordChecker.PasswordStrength.WEAK -> Red
                         },
                         textAlign = TextAlign.Start,
                         fontSize = 13.sp
                     )

                 }

                 Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

             }




            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.password_length),
                        color = KeySafeTheme.colors.text,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.small))

                    Slider(
                        value = viewModel.sliderPosition,
                        onValueChange = { newSlidePos->
                            viewModel.sliderPosition = newSlidePos
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = KeySafeTheme.colors.secondary,
                            activeTrackColor = KeySafeTheme.colors.primary,
                            inactiveTrackColor = Gray,
                        ),
                        valueRange = 8f..64f
                    )

                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.small))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(KeySafeTheme.spaces.medium),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = stringResource(R.string.min_8) ,
                            color = KeySafeTheme.colors.text,
                        )

                        Text(
                            text = stringResource(R.string.length, viewModel.sliderPosition.toInt()),
                            color = KeySafeTheme.colors.text,
                        )

                        Text(
                            text = stringResource(R.string.max_64),
                            color = KeySafeTheme.colors.text,
                        )

                    }
                }
                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

            }



            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {


                    CustomSwitch(
                        title = stringResource(R.string.use_capital_letters_a_z),
                        checked = viewModel.useCapitalLetters,
                        onCheckedChange = { viewModel.useCapitalLetters = it }
                    )

                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                    CustomSwitch(
                        title = stringResource(R.string.use_digits),
                        checked = viewModel.useDigits,
                        onCheckedChange = { viewModel.useDigits = it }
                    )

                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                    CustomSwitch(
                        title = stringResource(R.string.use_symbols),
                        checked = viewModel.useSymbols,
                        onCheckedChange = { viewModel.useSymbols = it },
                        enabled = viewModel.areUseNumbersAndSymbolsEnabled
                    )

                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                }
            }



        }

    }
}

