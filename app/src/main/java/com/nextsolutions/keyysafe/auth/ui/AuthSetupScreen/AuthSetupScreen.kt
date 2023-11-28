package com.nextsolutions.keyysafe.auth.ui.AuthSetupScreen

import android.widget.Toast
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.graphs.SetupNavigation
import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker
import com.nextsolutions.keyysafe.global_components.GreenButton
import com.nextsolutions.keyysafe.global_components.OutlinedOrangeButton
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange

@Composable
fun AuthSetupScreen(
    viewModel: AuthSetupViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current
    val activity = LocalContext.current as FragmentActivity
    val executor = ContextCompat.getMainExecutor(context)

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Authenticate to KeySafe")
        .setAllowedAuthenticators(BIOMETRIC_STRONG)
        .setNegativeButtonText("Cancel")
        .build()

    val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            Toast.makeText(context, errString, Toast.LENGTH_SHORT).show()
            viewModel.setupBiometricsTextState = "Try Again"
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Toast.makeText(context, "Biometric authentication failed", Toast.LENGTH_SHORT).show()
            viewModel.setupBiometricsTextState = "Try Again"
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            if (viewModel.pinState.isNotEmpty()){
                viewModel.savePin(viewModel.pinState.toInt())
            }
            viewModel.savePassword(viewModel.masterPasswordState)
            viewModel.saveBiometricAuth()
            navController.popBackStack()
            navController.navigate(SetupNavigation.OnBoardingScreen.route)
        }
    })

    LaunchedEffect(key1 = viewModel.masterPasswordState){
        if (viewModel.masterPasswordState.isNotEmpty()){
            viewModel.masterPasswordStrength = PasswordChecker().checkPasswordStrength(viewModel.masterPasswordState)
        }else{
            viewModel.masterPasswordStrength = null
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(KeySafeTheme.spaces.mediumLarge)
    ) {
        item {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = stringResource(id = R.string.app_name),
                    Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.width(KeySafeTheme.spaces.medium))

                Text(
                    text = stringResource(id = R.string.app_name),
                    color = KeySafeTheme.colors.text,
                    fontSize = 25.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                    )
                )

            }

            Text(
                text = stringResource(R.string.why_is_master_password_important),
                color = KeySafeTheme.colors.text,
            )

            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))


            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.masterPasswordState,
                    onValueChange = { newPassword -> viewModel.masterPasswordState = newPassword },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = KeySafeTheme.colors.text,
                        cursorColor = if (viewModel.masterPasswordState.isEmpty()) Orange else Green,
                        focusedBorderColor = when(viewModel.masterPasswordStrength){
                            PasswordChecker.PasswordStrength.STRONG -> Green
                            PasswordChecker.PasswordStrength.MEDIUM -> Orange
                            PasswordChecker.PasswordStrength.WEAK -> Color.Red
                            null -> Orange
                        },
                        unfocusedBorderColor = when(viewModel.masterPasswordStrength){
                            PasswordChecker.PasswordStrength.STRONG -> Green
                            PasswordChecker.PasswordStrength.MEDIUM -> Orange
                            PasswordChecker.PasswordStrength.WEAK -> Color.Red
                            null -> Orange
                        }
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (viewModel.showMasterPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.master_password),
                            color = KeySafeTheme.colors.text
                        )
                    },
                    trailingIcon = {
                        if (viewModel.showMasterPassword) {
                            IconButton(onClick = { viewModel.showMasterPassword = false }) {
                                Icon(
                                    imageVector = Icons.Filled.Visibility,
                                    contentDescription = stringResource(id = R.string.hide_show_password),
                                    tint = KeySafeTheme.colors.iconTint
                                )
                            }
                        } else {
                            IconButton(
                                onClick = { viewModel.showMasterPassword = true }) {
                                Icon(
                                    imageVector = Icons.Filled.VisibilityOff,
                                    contentDescription = stringResource(id = R.string.hide_show_password),
                                    tint = KeySafeTheme.colors.iconTint
                                )
                            }
                        }
                    },
                    singleLine = true
                )

                if (viewModel.masterPasswordStrength != null){
                    Text(
                        text = when(viewModel.masterPasswordStrength){
                            PasswordChecker.PasswordStrength.STRONG -> stringResource(R.string.password_is_strong)
                            PasswordChecker.PasswordStrength.MEDIUM -> stringResource(R.string.password_is_medium)
                            PasswordChecker.PasswordStrength.WEAK -> stringResource(R.string.password_is_weak)
                            null -> "not detected"
                        },
                        color = when(viewModel.masterPasswordStrength){
                            PasswordChecker.PasswordStrength.STRONG -> Green
                            PasswordChecker.PasswordStrength.MEDIUM -> Orange
                            PasswordChecker.PasswordStrength.WEAK -> Color.Red
                            null -> Color.Red
                        }
                    )
                }

            }

            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.confirmMasterPasswordState,
                onValueChange = { newPassword -> viewModel.confirmMasterPasswordState = newPassword },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = KeySafeTheme.colors.text,
                    cursorColor = if (viewModel.confirmMasterPasswordState.isEmpty()) Orange else Green,
                    focusedBorderColor = if (viewModel.confirmMasterPasswordState.isEmpty()) Orange else Green,
                    unfocusedBorderColor = if (viewModel.confirmMasterPasswordState.isEmpty()) Orange else Green
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (viewModel.showConfirmedMasterPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                label = {
                    Text(
                        text = stringResource(R.string.confirm_master_password),
                        color = KeySafeTheme.colors.text
                    )
                },
                trailingIcon = {
                    if (viewModel.showConfirmedMasterPassword) {
                        IconButton(onClick = { viewModel.showConfirmedMasterPassword = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = stringResource(id = R.string.hide_show_password),
                                tint = KeySafeTheme.colors.iconTint
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { viewModel.showConfirmedMasterPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = stringResource(id = R.string.hide_show_password),
                                tint = KeySafeTheme.colors.iconTint
                            )
                        }
                    }
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.pinState,
                onValueChange = { newPassword -> viewModel.pinState = newPassword },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = KeySafeTheme.colors.text,
                    cursorColor = if (viewModel.pinState.isEmpty()) Orange else Green,
                    focusedBorderColor = if (viewModel.pinState.isEmpty()) Orange else Green,
                    unfocusedBorderColor = if (viewModel.pinState.isEmpty()) Orange else Green
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = {
                    Text(
                        text = stringResource(R.string.pin_optional),
                        color = KeySafeTheme.colors.text
                    )
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

            GreenButton(
                onClick = {
                    if (viewModel.masterPasswordState.isEmpty()){
                        Toast.makeText(context, "Please add a master password", Toast.LENGTH_SHORT).show()
                        return@GreenButton
                    }
                    if (viewModel.confirmMasterPasswordState.isEmpty()){
                        Toast.makeText(context, "Please confirm your master password", Toast.LENGTH_SHORT).show()
                        return@GreenButton
                    }
                    if (viewModel.masterPasswordState != viewModel.confirmMasterPasswordState){
                        Toast.makeText(context, "Your passwords do not match", Toast.LENGTH_SHORT).show()
                        return@GreenButton
                    }
                    biometricPrompt.authenticate(promptInfo)
                },
                label = stringResource(R.string.setup_biometrics),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedOrangeButton(
                    onClick = {},
                    label = stringResource(R.string.faq),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                GreenButton(
                    onClick = {
                        if (viewModel.masterPasswordState.isEmpty()){
                            Toast.makeText(context, "Please add a master password", Toast.LENGTH_SHORT).show()
                            return@GreenButton
                        }
                        if (viewModel.confirmMasterPasswordState.isEmpty()){
                            Toast.makeText(context, "Please confirm your master password", Toast.LENGTH_SHORT).show()
                            return@GreenButton
                        }
                         if (viewModel.masterPasswordState != viewModel.confirmMasterPasswordState){
                             Toast.makeText(context, "Your passwords do not match", Toast.LENGTH_SHORT).show()
                             return@GreenButton
                         }
                        if (viewModel.pinState.isNotEmpty()){
                            viewModel.savePin(viewModel.pinState.toInt())
                        }
                        viewModel.savePassword(viewModel.masterPasswordState)
                        navController.popBackStack()
                        navController.navigate(SetupNavigation.OnBoardingScreen.route)
                    },
                    label = stringResource(R.string.continue_),
                    modifier = Modifier.weight(1f)
                )
            }

        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AuthSetupScreenPreview() {
    AuthSetupScreen(
        navController = rememberNavController()
    )
}