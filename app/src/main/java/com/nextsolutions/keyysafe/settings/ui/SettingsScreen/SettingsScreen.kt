package com.nextsolutions.keyysafe.settings.ui.SettingsScreen

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.graphs.SettingsNavigation
import com.nextsolutions.keyysafe.common.util.Other
import com.nextsolutions.keyysafe.global_components.BackTopAppBar
import com.nextsolutions.keyysafe.settings.domain.enums.SettingsItemType
import com.nextsolutions.keyysafe.settings.domain.models.SettingsItem.Companion.getSettingsItems
import com.nextsolutions.keyysafe.settings.ui.SettingsScreen.components.SettingsItem
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun SettingsScreen(
    navController: NavController
) {

    val context = LocalContext.current

    Scaffold(
        backgroundColor = KeySafeTheme.colors.background,
        topBar = {
            BackTopAppBar(
                title = stringResource(R.string.settings),
                onBackPress = {
                    navController.navigateUp()
                }
            )
        }
    ){ padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(KeySafeTheme.spaces.mediumLarge)
                .fillMaxSize()
        ){




            items(getSettingsItems(context)){ settingsItem ->
                SettingsItem(
                    data = settingsItem,
                    onClick = { settingsItemType ->
                        when(settingsItemType){
                            SettingsItemType.Appearance -> {
                                navController.navigate(SettingsNavigation.Appearance.route)
                            }
                            SettingsItemType.Security -> {
                                navController.navigate(SettingsNavigation.Security.route)
                            }
                            SettingsItemType.Database -> {
                                if (Other.hasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                                    navController.navigate(SettingsNavigation.Database.route)
                                }else{
                                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    val uri = Uri.fromParts("package", context.packageName, null)
                                    intent.data = uri
                                    context.startActivity(intent)
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.grant_the_allow_managemnt_of_all_files_permission_for_to_be_able_to_use_the_database),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            SettingsItemType.About -> {
                                navController.navigate(SettingsNavigation.About.route)
                            }
                            SettingsItemType.Privacy_Policy -> {
                                val privacyPolicyUrl = "https://www.freeprivacypolicy.com/live/9ceee5bb-37c7-44e8-a379-15f5d5aa17dd"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl))
                                context.startActivity(intent)
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))

            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                item {
//                    CustomSwitch(
//                        title = stringResource(id = R.string.auto_fill),
//                        subtitle = stringResource(R.string.automatically_fill_password_in_to_websites_and_applications),
//                        checked = context.isAppSetAsAutofillService(),
//                        onClick = {
//                            navController.navigate(MainNavigation.AutoFill.route)
//                        }
//                    )
//                }
//            }

        }
    }
}