package com.nextsolutions.keyysafe.auth.ui.AuthSetupScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.common.PasswordChecker
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreKeys
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreManager
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesKeys
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthSetupViewModel @Inject constructor(
    private val prefs: PreferencesManager
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