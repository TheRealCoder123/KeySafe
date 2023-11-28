package com.nextsolutions.keyysafe.settings.ui.Appearance

import android.Manifest
import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.common.util.Other
import com.nextsolutions.keyysafe.global_components.BackTopAppBar
import com.nextsolutions.keyysafe.global_components.CustomSwitch
import com.nextsolutions.keyysafe.settings.settings_components.TextComponent
import com.nextsolutions.keyysafe.settings.ui.Appearance.components.AppLanguage
import com.nextsolutions.keyysafe.settings.ui.Appearance.components.AppThemeDialog
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun AppearanceScreen(
    navHostController: NavHostController,
    viewModel: AppearanceViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val activity = context as FragmentActivity

    AppThemeDialog(
        isVisible = viewModel.isAppThemeDialogVisible,
        onThemeClicked = {
            viewModel.changeTheme(it)
        },
        onDismiss = {
            viewModel.isAppThemeDialogVisible = false
        }
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        LaunchedEffect(key1 = Unit){
            val currentAppLanguage = context.getSystemService(LocaleManager::class.java).applicationLocales.toLanguageTags()
            viewModel.currentAppLanguageSelected = currentAppLanguage
            Log.e("currently selected lang Android 13", currentAppLanguage)
        }
    }




    AppLanguage(
        isVisible = viewModel.isAppLanguageDialogVisible,
        selectedLanguage = viewModel.currentAppLanguageSelected,
        onLanguageClicked = {
            viewModel.currentAppLanguageSelected = it
            setLanguage(it, context, activity, viewModel)
        },
        onDismiss = {
            viewModel.isAppLanguageDialogVisible = false
        }
    )

    val postNotificationPerm = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            if (it){
                viewModel.showNotification = true
                viewModel.showNotification()
            }else{
                Toast.makeText(
                    context,
                    context.getString(R.string.post_notification_permission_is_not_granted),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )


    Scaffold(
        backgroundColor = KeySafeTheme.colors.background,
        topBar = {
            BackTopAppBar(
                title = stringResource(id = R.string.appearance),
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
                TextComponent(
                    title = stringResource(R.string.app_theme),
                    subTitle = viewModel.currentThemeSelected?.name ?: stringResource(R.string.theme),
                    onClick = {
                        viewModel.isAppThemeDialogVisible = true
                    }
                )

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                TextComponent(
                    title = stringResource(R.string.app_language),
                    subTitle = when(viewModel.currentAppLanguageSelected){
                        "en" -> "English"
                        "mk" -> "Macedonian"
                        "de" -> "German"
                        "pt" -> "Portuguese"
                        "es" -> "Spanish"
                        "sr" -> "Serbian"
                        else -> "English"
                    },
                    onClick = {
                        viewModel.isAppLanguageDialogVisible = true
                    }
                )

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                CustomSwitch(
                    title = stringResource(R.string.authentication_screen),
                    subtitle = stringResource(R.string.open_authentication_screen_when_you_come_back_from_to_the_app),
                    checked = viewModel.openAuthScreenWhenBackToApp,
                    onCheckedChange = {
                        viewModel.openAuthScreenWhenBackToApp = it
                        viewModel.openAuthOnStart()
                    }
                )

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                CustomSwitch(
                    title = stringResource(R.string.notification),
                    subtitle = stringResource(R.string.show_a_notification_whenever_the_app_is_open),
                    checked = viewModel.showNotification,
                    onCheckedChange = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            if (!Other.hasPermission(context, Manifest.permission.POST_NOTIFICATIONS)){
                                postNotificationPerm.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }else{
                                viewModel.showNotification = it
                                viewModel.showNotification()
                            }
                        }else{
                            viewModel.showNotification = it
                            viewModel.showNotification()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                CustomSwitch(
                    title = stringResource(R.string.navigation_bar_color),
                    subtitle = stringResource(R.string.uses_the_current_theme_main_color_and_applies_it_on_the_navigation_bar),
                    checked = viewModel.navBarColor,
                    onCheckedChange = {
                        viewModel.navBarColor = it
                        viewModel.navBarColor()
                    }
                )

            }

        }

    }


}

private fun setLanguage(lang: String, context: Context, activity: FragmentActivity, viewModel: AppearanceViewModel) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        context.getSystemService(LocaleManager::class.java).applicationLocales = LocaleList.forLanguageTags(lang)
    }else{
        viewModel.changeLocale(lang)
        activity.recreate()
    }
}