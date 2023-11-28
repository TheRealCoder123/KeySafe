package com.nextsolutions.keyysafe.auth.ui.AuthSetupScreen

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesKeys
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesManager
import com.nextsolutions.keyysafe.common.password_manager.PasswordEncrypt
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.inject.Inject

@HiltViewModel
class AuthSetupViewModel @Inject constructor(
    private val prefs: PreferencesManager,
) : ViewModel() {

    var masterPasswordState by mutableStateOf("")
    var confirmMasterPasswordState by mutableStateOf("")
    var pinState by mutableStateOf("")
    var showMasterPassword by mutableStateOf(false)
    var showConfirmedMasterPassword by mutableStateOf(false)
    var masterPasswordStrength by mutableStateOf<PasswordChecker.PasswordStrength?>(null)
    var setupBiometricsTextState by mutableStateOf("Setup Biometrics")

    fun savePassword(password: String) {
        prefs.saveString(PreferencesKeys.MASTER_PASSWORD_KEY, password)
    }

    fun savePin(pin: Int) {
        prefs.saveInt(PreferencesKeys.PIN_KEY, pin)
    }

    fun saveBiometricAuth() {
        prefs.saveBool(PreferencesKeys.HAS_BIOMETRIC_AUTH, true)
    }

}