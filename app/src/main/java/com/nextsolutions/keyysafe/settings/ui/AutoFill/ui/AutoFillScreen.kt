package com.nextsolutions.keyysafe.settings.ui.AutoFill.ui

import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.autofill.AutofillManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.global_components.GreenButton
import com.nextsolutions.keyysafe.global_components.OutlinedOrangeButton
import com.nextsolutions.keyysafe.settings.ui.AutoFill.components.AnimatedBorderCard
import com.nextsolutions.keyysafe.settings.ui.AutoFill.components.StepsComponent
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun AutoFillScreen(navHostController: NavHostController) {

    val context = LocalContext.current

    val requestAutofillServiceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {

        } else {

        }
    }

    Scaffold(
        backgroundColor = KeySafeTheme.colors.background,
        topBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = KeySafeTheme.spaces.mediumLarge),
                text = stringResource(id = R.string.autofill_service),
                color = KeySafeTheme.colors.text,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .padding(horizontal = KeySafeTheme.spaces.mediumLarge)
        ) {


            item {

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))

                AnimatedBorderCard(
                    shape = RoundedCornerShape(KeySafeTheme.spaces.mediumLarge)
                ) {

                    Column(
                        modifier = Modifier.padding(KeySafeTheme.spaces.mediumLarge),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.why_is_autofill_useful),
                            color = KeySafeTheme.colors.text,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Bold)
                                ){
                                    append(stringResource(R.string._1_time_saver))
                                }
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Medium)
                                ){
                                    append(stringResource(R.string.autofill_speeds_data_entry))
                                }
                            },
                            color = KeySafeTheme.colors.text,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.medium))

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Bold)
                                ){
                                    append(stringResource(R.string._2_error_minimizer))
                                }
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Medium)
                                ){
                                    append(stringResource(R.string.reduces_user_mistakes))
                                }
                            },
                            color = KeySafeTheme.colors.text,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.medium))

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Bold)
                                ){
                                    append(stringResource(R.string._3_enhanced_security))
                                }
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Medium)
                                ){
                                    append(stringResource(R.string.safely_stores_sensitive_info))
                                }
                            },
                            color = KeySafeTheme.colors.text,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                    }



                }

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))


                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.turn_on_autofill_on_keysafe),
                    color = KeySafeTheme.colors.text,
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))

                StepsComponent(number = 1, buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Medium)
                    ){
                        append(stringResource(R.string.open))
                    }
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold)
                    ){
                        append(stringResource(R.string.autofill_service_capital))
                    }
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Medium)
                    ){
                        append(stringResource(R.string.in_your_device_settings))
                    }
                })

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                StepsComponent(number = 2, buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Medium)
                    ){
                        append(stringResource(R.string.select))
                    }
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold)
                    ){
                        append(stringResource(id = R.string.app_name))
                    }
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Medium)
                    ){
                        append(stringResource(R.string.as_))
                    }
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold)
                    ){
                        append(stringResource(R.string.autofill_service_capital))
                    }
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Medium)
                    ){
                        append(stringResource(R.string.in_the_list))
                    }
                })

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                StepsComponent(number = 3, buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Medium)
                    ){
                        append(stringResource(R.string.confirm_you_that_you_trust_the_app))
                    }
                })

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                StepsComponent(number = 4, buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold)
                    ){
                        append(stringResource(R.string.done))
                    }
                })

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedOrangeButton(
                        onClick = {
                            navHostController.navigateUp()
                        },
                        label = stringResource(R.string.not_now),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                    GreenButton(
                        onClick = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                val autoFillManager = context.getSystemService(AutofillManager::class.java)
                                if (!autoFillManager.hasEnabledAutofillServices() && autoFillManager.isAutofillSupported) {
                                    val intent = Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE)
                                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                                    requestAutofillServiceLauncher.launch(intent)
                                }

                            }
                        },
                        label = stringResource(R.string.continue_),
                        modifier = Modifier.weight(1f)
                    )
                }

            }

        }
    }







}