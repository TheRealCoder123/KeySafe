package com.nextsolutions.keyysafe.settings.ui.Security.ChangeMasterPasswordScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.nextsolutions.keyysafe.auth.domain.enums.AuthenticationType
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesKeys
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesManager
import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeMasterPasswordViewModel @Inject constructor(
    private val prefs: PreferencesManager
) : ViewModel() {


    var oldMasterPasswordTextState by mutableStateOf("")
    var oldMasterPassword by mutableStateOf("")
    var newMasterPassword by mutableStateOf("")
    var confirmNewMasterPassword by mutableStateOf("")
    var changeWithPinTextState by mutableStateOf("")
    var changeWithPin by mutableStateOf("")
    var isRightMasterPasswordOrPin by mutableStateOf(false)
    var changePasswordType by mutableStateOf(AuthenticationType.PASSWORD)
    var passwordStrengthState by mutableStateOf(PasswordChecker.PasswordStrength.WEAK)


    init {
        getCurrentMasterPassword()
        getCurrentPin()
    }

    fun setAuthenticationType(type: AuthenticationType){
        changePasswordType = type
        isRightMasterPasswordOrPin = false
        oldMasterPasswordTextState = ""
        changeWithPinTextState = ""
    }

    fun analyzeNewPasswordStrength() {
        PasswordChecker().checkPasswordStrength(newMasterPassword).let {
            passwordStrengthState = it
        }
    }

    private fun getCurrentMasterPassword() {
        val currentMasterPassword = prefs.getString(PreferencesKeys.MASTER_PASSWORD_KEY, "")
        oldMasterPassword = currentMasterPassword ?: ""
    }

    private fun getCurrentPin() {
        val currentPin = prefs.getInt(PreferencesKeys.PIN_KEY, 0)
        changeWithPin = currentPin.toString()
    }

    fun changeMasterPassword() {
        prefs.saveString(PreferencesKeys.MASTER_PASSWORD_KEY, newMasterPassword)
    }

}