package com.nextsolutions.keyysafe.settings.ui.Security.ChangeMasterPasswordScreen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.auth.domain.enums.AuthenticationType
import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker
import com.nextsolutions.keyysafe.global_components.BackTopAppBar
import com.nextsolutions.keyysafe.global_components.CustomTextField
import com.nextsolutions.keyysafe.global_components.GreenButton
import com.nextsolutions.keyysafe.global_components.OtpTextField
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange
import com.nextsolutions.keyysafe.ui.theme.White

@Composable
fun ChangeMasterPasswordScreen(
    navHostController: NavHostController,
    viewModel: ChangeMasterPasswordViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.oldMasterPasswordTextState){
        viewModel.isRightMasterPasswordOrPin = viewModel.oldMasterPasswordTextState == viewModel.oldMasterPassword
    }

    LaunchedEffect(key1 = viewModel.changeWithPinTextState){
        viewModel.isRightMasterPasswordOrPin = viewModel.changeWithPinTextState == viewModel.changeWithPin
    }

    LaunchedEffect(key1 = viewModel.newMasterPassword){
        if (viewModel.newMasterPassword.isNotEmpty()){
            viewModel.analyzeNewPasswordStrength()
        }
    }

    val passwordColorAnim by animateColorAsState(
        targetValue = if (viewModel.changePasswordType == AuthenticationType.PASSWORD) Green else KeySafeTheme.colors.background,
        label = "Password_Animation"
    )

    val pinColorAnim by animateColorAsState(
        targetValue = if (viewModel.changePasswordType == AuthenticationType.PIN) Green else KeySafeTheme.colors.background,
        label = "PIN_Animation"
    )

    Scaffold(
        backgroundColor = KeySafeTheme.colors.background,
        topBar = {
            BackTopAppBar(
                title = stringResource(R.string.change_master_password),
                onBackPress = {
                    navHostController.navigateUp()
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

            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    GreenButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            viewModel.setAuthenticationType(AuthenticationType.PASSWORD)
                        },
                        label = stringResource(R.string.password),
                        shape = RoundedCornerShape(KeySafeTheme.spaces.extraLarge),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = passwordColorAnim,
                            contentColor = when(viewModel.changePasswordType){
                                AuthenticationType.PASSWORD -> White
                                AuthenticationType.PIN -> Orange
                            }
                        ),
                        elevation = ButtonDefaults.elevation(0.dp,0.dp,0.dp,0.dp,0.dp)
                    )

                    GreenButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            viewModel.setAuthenticationType(AuthenticationType.PIN)
                        },
                        label = stringResource(R.string.pin),
                        shape = RoundedCornerShape(KeySafeTheme.spaces.extraLarge),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = pinColorAnim,
                            contentColor = when(viewModel.changePasswordType){
                                AuthenticationType.PASSWORD -> Orange
                                AuthenticationType.PIN -> White
                            }
                        ),
                        elevation = ButtonDefaults.elevation(0.dp,0.dp,0.dp,0.dp,0.dp)
                    )


                }

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                Box(
                    modifier = Modifier.fillMaxWidth()
                ){
                    AnimatedVisibility(
                        modifier = Modifier.fillMaxWidth(),
                        visible = viewModel.changePasswordType == AuthenticationType.PASSWORD,
                        enter = fadeIn() + expandHorizontally(),
                        exit = fadeOut() + shrinkHorizontally()
                    ){
                        CustomTextField(
                            value = viewModel.oldMasterPasswordTextState,
                            onValueChange = { viewModel.oldMasterPasswordTextState = it },
                            label = stringResource(id = R.string.old_master_password),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )

                    }

                    AnimatedVisibility(
                        modifier = Modifier.fillMaxWidth(),
                        visible = viewModel.changePasswordType == AuthenticationType.PIN,
                        enter = fadeIn() + expandHorizontally(),
                        exit = fadeOut() + shrinkHorizontally()
                    ){
                        OtpTextField(
                            modifier = Modifier.fillMaxWidth(),
                            otpText = viewModel.changeWithPinTextState,
                            onOtpTextChange = { newInput, _ ->
                                viewModel.changeWithPinTextState = newInput
                            },
                            otpCount = 4
                        )
                    }
                }

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    visible = viewModel.isRightMasterPasswordOrPin,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ){
                    Text(
                        text = stringResource(R.string.correct_authentication_details),
                        color = Green
                    )
                }

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                Column {
                    CustomTextField(
                        value = viewModel.newMasterPassword,
                        onValueChange = { viewModel.newMasterPassword = it },
                        label = stringResource(R.string.new_master_password),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                    AnimatedVisibility(
                        modifier = Modifier.fillMaxWidth(),
                        visible = viewModel.newMasterPassword.isNotEmpty(),
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ){
                        Text(
                            text = when(viewModel.passwordStrengthState){
                                PasswordChecker.PasswordStrength.STRONG -> stringResource(R.string.password_is_strong)
                                PasswordChecker.PasswordStrength.MEDIUM -> stringResource(R.string.password_is_medium)
                                PasswordChecker.PasswordStrength.WEAK -> stringResource(R.string.password_is_weak)
                            },
                            color = when(viewModel.passwordStrengthState){
                                PasswordChecker.PasswordStrength.STRONG -> Green
                                PasswordChecker.PasswordStrength.MEDIUM -> Orange
                                PasswordChecker.PasswordStrength.WEAK -> Color.Red
                            }
                        )
                    }

                }

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                CustomTextField(
                    value = viewModel.confirmNewMasterPassword,
                    onValueChange = { viewModel.confirmNewMasterPassword = it },
                    label = stringResource(R.string.confirm_new_master_password),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                GreenButton(
                    modifier = Modifier.fillMaxWidth(),
                    isEnabled = viewModel.oldMasterPassword == viewModel.oldMasterPasswordTextState || viewModel.changeWithPin == viewModel.changeWithPinTextState,
                    onClick = {


                        if (viewModel.oldMasterPasswordTextState.isEmpty() && viewModel.changeWithPinTextState.isEmpty()){
                            Toast.makeText(
                                context,
                                context.getString(R.string.enter_your_old_master_password_or_your_pin),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@GreenButton
                        }

                        if (viewModel.newMasterPassword.isEmpty()){
                            Toast.makeText(
                                context,
                                context.getString(R.string.please_enter_your_new_master_password),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@GreenButton
                        }

                        if (viewModel.confirmNewMasterPassword.isEmpty()){
                            Toast.makeText(
                                context,
                                context.getString(R.string.please_confirm_your_new_master_password),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@GreenButton
                        }

                        if (viewModel.newMasterPassword != viewModel.confirmNewMasterPassword){
                            Toast.makeText(
                                context,
                                context.getString(R.string.your_new_master_passwords_do_not_match),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@GreenButton
                        }
                        viewModel.changeMasterPassword()
                        Toast.makeText(
                            context,
                            context.getString(R.string.your_master_password_has_been_successfully_changed),
                            Toast.LENGTH_SHORT
                        ).show()
                        navHostController.navigateUp()
                    },
                    label = stringResource(R.string.change_master_password),
                )

            }

        }
    }

}