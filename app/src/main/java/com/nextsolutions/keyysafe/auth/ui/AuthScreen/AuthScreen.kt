package com.nextsolutions.keyysafe.auth.ui.AuthScreen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.MainViewModel
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.app.graphs.Navigation
import com.nextsolutions.keyysafe.auth.domain.enums.AuthScreenUsability
import com.nextsolutions.keyysafe.auth.domain.enums.AuthenticationType
import com.nextsolutions.keyysafe.common.util.ArgumentKeys
import com.nextsolutions.keyysafe.global_components.GreenButton
import com.nextsolutions.keyysafe.global_components.OtpTextField
import com.nextsolutions.keyysafe.global_components.OutlinedOrangeButton
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange
import com.nextsolutions.keyysafe.ui.theme.White

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController,
    mainViewModel: MainViewModel
) {


    val state = authViewModel.state.value

    val context = LocalContext.current
    val activity = LocalContext.current as FragmentActivity
    val executor = ContextCompat.getMainExecutor(context)

    val passwordColorAnim by animateColorAsState(
        targetValue = if (state.authenticationType == AuthenticationType.PASSWORD) Green else KeySafeTheme.colors.background,
        label = "Password_Animation"
    )

    val pinColorAnim by animateColorAsState(
        targetValue = if (state.authenticationType == AuthenticationType.PIN) Green else KeySafeTheme.colors.background,
        label = "PIN_Animation"
    )



    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Authenticate to KeySafe")
        .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        .setNegativeButtonText("Cancel")
        .build()

    val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            Toast.makeText(context, errString, Toast.LENGTH_SHORT).show()
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Toast.makeText(context, "Biometric authentication failed", Toast.LENGTH_SHORT).show()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            authViewModel.isBiometricAuthenticated()
        }
    })



    LaunchedEffect(key1 = authViewModel.passwordTextState){
        if (authViewModel.passwordTextState == authViewModel.masterPassword){
            authViewModel.authenticateWithPassword(authViewModel.passwordTextState)
        }
    }

    LaunchedEffect(key1 = authViewModel.pinTextState){
        if (authViewModel.pinTextState.isNotEmpty() && authViewModel.pinTextState.toInt() == authViewModel.pin && authViewModel.pin != 0){
            authViewModel.authenticateWithPIN(authViewModel.pinTextState.toInt())
        }
    }

    LaunchedEffect(key1 = state.error){
        if (state.error.isNotEmpty()){
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = state.isAuthenticated){
        if (state.isAuthenticated){
            when(authViewModel.screenUsability){
                AuthScreenUsability.AUTHENTICATE -> {
                    navController.popBackStack()
                    navController.navigate(MainNavigation.Dashboard.route)
                }
                AuthScreenUsability.RE_AUTHENTICATE -> {
                    mainViewModel.shouldOpenAuthScreen = false
                    navController.popBackStack()
                }
                AuthScreenUsability.CONFIRM_AUTHENTICATION -> {
                    navController.popBackStack()
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set(ArgumentKeys.AUTH_SCREEN_CONFIRM_AUTH_RESULT_KEY, true)
                }
            }
        }
    }

    BackHandler {
        when(authViewModel.screenUsability){
            AuthScreenUsability.AUTHENTICATE -> activity.finish()
            AuthScreenUsability.RE_AUTHENTICATE -> activity.finish()
            AuthScreenUsability.CONFIRM_AUTHENTICATION -> {
                navController.popBackStack()
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set(ArgumentKeys.AUTH_SCREEN_CONFIRM_AUTH_RESULT_KEY, false)
            }
        }
    }
    LaunchedEffect(key1 = Unit){
        if (authViewModel.hasBiometricAuth){
            biometricPrompt.authenticate(promptInfo)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(KeySafeTheme.spaces.mediumLarge),
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


            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                GreenButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        authViewModel.setAuthenticationType(AuthenticationType.PASSWORD)
                    },
                    label = stringResource(R.string.password),
                    shape = RoundedCornerShape(KeySafeTheme.spaces.extraLarge),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = passwordColorAnim,
                        contentColor = when(state.authenticationType){
                            AuthenticationType.PASSWORD -> White
                            AuthenticationType.PIN -> Orange
                        }
                    ),
                    elevation = ButtonDefaults.elevation(0.dp,0.dp,0.dp,0.dp,0.dp)
                )

                GreenButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        authViewModel.setAuthenticationType(AuthenticationType.PIN)
                    },
                    label = stringResource(R.string.pin),
                    shape = RoundedCornerShape(KeySafeTheme.spaces.extraLarge),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = pinColorAnim,
                        contentColor = when(state.authenticationType){
                            AuthenticationType.PASSWORD -> Orange
                            AuthenticationType.PIN -> White
                        }
                    ),
                    elevation = ButtonDefaults.elevation(0.dp,0.dp,0.dp,0.dp,0.dp)
                )


            }

            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))


            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    visible = state.authenticationType == AuthenticationType.PASSWORD,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + shrinkHorizontally()
                ){
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = authViewModel.passwordTextState,
                        onValueChange = { newPassword -> authViewModel.passwordTextState = newPassword },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = KeySafeTheme.colors.text,
                            cursorColor = if (authViewModel.passwordTextState.isEmpty()) Orange else Green,
                            focusedBorderColor = if (authViewModel.passwordTextState.isEmpty()) Orange else Green,
                            unfocusedBorderColor = if (authViewModel.passwordTextState.isEmpty()) Orange else Green
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        label = {
                            Text(
                                text = stringResource(R.string.master_password),
                                color = KeySafeTheme.colors.text
                            )
                        },
                        singleLine = true
                    )
                }

                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    visible = state.authenticationType == AuthenticationType.PIN,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + shrinkHorizontally()
                ){
                    OtpTextField(
                        modifier = Modifier.fillMaxWidth(),
                        otpText = authViewModel.pinTextState,
                        onOtpTextChange = { newInput, currentInputField ->
                            authViewModel.pinTextState = newInput
                        },
                        otpCount = 4
                    )
                }
            }


            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedOrangeButton(
                    onClick = {
                        navController.navigate(Navigation.FAQScreen.route)
                    },
                    label = stringResource(R.string.faq),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                GreenButton(
                    onClick = {
                        biometricPrompt.authenticate(promptInfo)
                    },
                    label = stringResource(R.string.use_biometrics),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))


            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = state.authenticationType == AuthenticationType.PIN && authViewModel.pin == 0,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally()
            ){
                GreenButton(
                    onClick = {
                        authViewModel.savePIN(authViewModel.pinTextState.toInt())
                    },
                    label = stringResource(R.string.add_pin),
                    modifier = Modifier.fillMaxWidth()
                )
            }



        }
    }
}