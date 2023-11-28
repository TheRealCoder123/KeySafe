package com.nextsolutions.keyysafe.settings.ui.About.AboutScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.common.AppLaucnher.AppLauncherResult
import com.nextsolutions.keyysafe.common.AppLaucnher.OtherAppLauncher
import com.nextsolutions.keyysafe.common.AppLaucnher.OtherAppLauncher.sendMail
import com.nextsolutions.keyysafe.global_components.BackTopAppBar
import com.nextsolutions.keyysafe.settings.settings_components.TextComponent
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun AboutScreen(
    navHostController: NavHostController
) {
    val context = LocalContext.current

    Scaffold(
        backgroundColor = KeySafeTheme.colors.background,
        topBar = {
            BackTopAppBar(
                title = stringResource(id = R.string.about),
                onBackPress = {
                    navHostController.navigateUp()
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(KeySafeTheme.spaces.mediumLarge)
        ){

            item {

                Text(
                    modifier = Modifier.clickable {
                        when(context.sendMail(OtherAppLauncher.MY_EMAIL,"Im reporting a bug about the KeySafe app...")){
                            AppLauncherResult.NO_APP -> Toast.makeText(
                                context,
                                context.getString(R.string.no_mail_app_found),
                                Toast.LENGTH_SHORT
                            ).show()
                            AppLauncherResult.APP_FOUND -> Toast.makeText(
                                context,
                                context.getString(R.string.select_mail_app),
                                Toast.LENGTH_SHORT
                            ).show()
                            AppLauncherResult.OTHER_ERROR -> Toast.makeText(
                                context,
                                context.getString(R.string.there_was_an_error_oppening_mail_apps),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    text = stringResource(R.string.report_a_bug),
                    color = KeySafeTheme.colors.text,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                Text(
                    modifier = Modifier.clickable {
                        when(context.sendMail(OtherAppLauncher.MY_EMAIL,"Im suggesting a really cool feature...")) {
                            AppLauncherResult.NO_APP -> Toast.makeText(
                                context,
                                context.getString(R.string.no_mail_app_found),
                                Toast.LENGTH_SHORT
                            ).show()
                            AppLauncherResult.APP_FOUND -> Toast.makeText(
                                context,
                                context.getString(R.string.select_mail_app),
                                Toast.LENGTH_SHORT
                            ).show()
                            AppLauncherResult.OTHER_ERROR -> Toast.makeText(
                                context,
                                context.getString(R.string.there_was_an_error_oppening_mail_apps),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    text = stringResource(R.string.suggest_a_feature),
                    color = KeySafeTheme.colors.text,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

//                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))
//
//                Text(
//                    text = stringResource(R.string.rate_and_review),
//                    color = KeySafeTheme.colors.text,
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold
//                )

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                TextComponent(
                    title = stringResource(R.string.app_version),
                    subTitle = "1.0"
                )

            }


        }
    }
}