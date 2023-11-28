package com.nextsolutions.keyysafe.settings.ui.Security.SecurityScreen

import android.widget.Toast
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.graphs.SettingsNavigation
import com.nextsolutions.keyysafe.global_components.BackTopAppBar
import com.nextsolutions.keyysafe.global_components.CustomSwitch
import com.nextsolutions.keyysafe.settings.settings_components.TextComponent
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme



@Composable
fun SecurityScreen(
    navHostController: NavHostController,
    viewModel: SecurityViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val biometricManager = androidx.biometric.BiometricManager.from(context)

    LaunchedEffect(key1 = viewModel.biometricAuth){
        if (viewModel.biometricAuth){
            when(biometricManager.canAuthenticate()){
                androidx.biometric.BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    viewModel.biometricAuth = false
                    viewModel.changeBiometricAuth()
                    Toast.makeText(
                        context,
                        context.getString(R.string.biometric_hardware_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    viewModel.biometricAuth = false
                    viewModel.changeBiometricAuth()
                    Toast.makeText(
                        context,
                        context.getString(R.string.none_biometric_hardware_enrolled),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    viewModel.biometricAuth = false
                    viewModel.changeBiometricAuth()
                    Toast.makeText(
                        context,
                        context.getString(R.string.no_biometric_hardware),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                androidx.biometric.BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                    viewModel.biometricAuth = false
                    viewModel.changeBiometricAuth()
                    Toast.makeText(
                        context,
                        context.getString(R.string.biometric_update),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                androidx.biometric.BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                    viewModel.biometricAuth = false
                    viewModel.changeBiometricAuth()
                    Toast.makeText(
                        context,
                        context.getString(R.string.biometric_hardware_not_supported),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                androidx.biometric.BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                    viewModel.biometricAuth = false
                    viewModel.changeBiometricAuth()
                    Toast.makeText(
                        context,
                        context.getString(R.string.biometric_hardware_status_unknown),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS -> {
                    viewModel.changeBiometricAuth()
                }
            }
        }
    }



    Scaffold(
        backgroundColor = KeySafeTheme.colors.background,
        topBar = {
            BackTopAppBar(
                title = stringResource(id = R.string.security),
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
        ) {


            item {

                TextComponent(
                    title = stringResource(R.string.change_master_password),
                    subTitle = stringResource(R.string.do_not_change_your_password_too_often_you_might_forget_it),
                    onClick = {
                        navHostController.navigate(SettingsNavigation.ChangeMasterPassword.route)
                    }
                )

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                CustomSwitch(
                    title = stringResource(R.string.biometric_authentication),
                    subtitle = stringResource(R.string.for_this_feature_a_compatible_device_is_required_you_can_log_in_the_application_with_biometrics_like_fingerprint_and_face_recognition),
                    checked = viewModel.biometricAuth,
                    onCheckedChange = {
                        viewModel.biometricAuth = it
                    }
                )

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                CustomSwitch(
                    title = stringResource(R.string.allow_screenshots),
                    subtitle = stringResource(R.string.you_can_allow_screenshots_to_be_taken_of_your_app_but_remember_this_could_resolve_in_bad_consequences),
                    checked = viewModel.allowScreenShots,
                    onCheckedChange = {
                        viewModel.allowScreenShots = it
                        viewModel.changeAllowedScreenShots()
                    }
                )


            }


        }
    }


}