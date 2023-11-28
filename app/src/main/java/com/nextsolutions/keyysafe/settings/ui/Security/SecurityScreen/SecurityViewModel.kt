package com.nextsolutions.keyysafe.settings.ui.Security.SecurityScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreKeys
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreManager
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesKeys
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesManager
import com.nextsolutions.keyysafe.common.util.SettingsRules
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecurityViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val prefs: PreferencesManager
) : ViewModel() {


    var biometricAuth by mutableStateOf(false)
    var allowScreenShots by mutableStateOf(false)

    init {
        biometricAuth = prefs.getBool(PreferencesKeys.HAS_BIOMETRIC_AUTH, false)
        getAllowedScreenShots()
    }

    private fun getAllowedScreenShots() = viewModelScope.launch {
        dataStoreManager.get(DataStoreKeys.AllowScreenShots).collectLatest {
            if (it != null){
                allowScreenShots = it
            }
        }
    }

    fun changeAllowedScreenShots() = viewModelScope.launch {
        dataStoreManager.save(DataStoreKeys.AllowScreenShots, allowScreenShots)
    }

    fun changeBiometricAuth() {
        prefs.saveBool(PreferencesKeys.HAS_BIOMETRIC_AUTH, biometricAuth)
    }




}