package com.nextsolutions.keyysafe.auth.ui.AuthScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.auth.domain.enums.AuthScreenUsability
import com.nextsolutions.keyysafe.auth.domain.enums.AuthenticationType
import com.nextsolutions.keyysafe.auth.domain.enums.decodeAuthScreenUsability
import com.nextsolutions.keyysafe.auth.domain.repository.AuthRepository
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesKeys
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesManager
import com.nextsolutions.keyysafe.common.util.ArgumentKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val prefs: PreferencesManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(AuthState())
    val state : State<AuthState> = _state

    var pinTextState by mutableStateOf("")
    var passwordTextState by mutableStateOf("")
    var masterPassword : String = ""
    var pin: Int = 0
    var hasBiometricAuth: Boolean = false
    var screenUsability by mutableStateOf<AuthScreenUsability>(AuthScreenUsability.AUTHENTICATE)

    init {
        masterPassword = prefs.getString(PreferencesKeys.MASTER_PASSWORD_KEY, null) ?: ""
        pin = prefs.getInt(PreferencesKeys.PIN_KEY, 0)
        hasBiometricAuth = prefs.getBool(PreferencesKeys.HAS_BIOMETRIC_AUTH, false)
        val usability = savedStateHandle.get<String>(ArgumentKeys.AUTH_SCREEN_USABILITY)
        if (!usability.isNullOrEmpty()){
            screenUsability = decodeAuthScreenUsability(usability)
        }
    }

    fun setAuthenticationType(type: AuthenticationType){
        _state.value = _state.value.copy(authenticationType = type)
    }

    fun authenticateWithPassword(password: String) = viewModelScope.launch {
        authRepository.authenticateWithPassword(password).let {
            _state.value = AuthState(it.isAuthenticated, it.error)
        }
    }

    fun authenticateWithPIN(pin: Int) = viewModelScope.launch {
        authRepository.authenticateWithPIN(pin).let {
            _state.value = AuthState(it.isAuthenticated, it.error)
        }
    }

    fun isBiometricAuthenticated() {
        _state.value = AuthState(true)
    }


    fun savePIN(pin: Int){
        prefs.saveInt(PreferencesKeys.PIN_KEY, pin)
        authenticateWithPIN(pin)
    }


}