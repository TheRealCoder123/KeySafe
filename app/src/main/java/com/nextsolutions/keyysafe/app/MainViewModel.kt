package com.nextsolutions.keyysafe.app

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreKeys
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreManager
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesKeys
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesManager
import com.nextsolutions.keyysafe.common.util.AppTheme
import com.nextsolutions.keyysafe.common.util.SettingsRules
import com.nextsolutions.keyysafe.common.util.toAppThemeEnum
import com.nextsolutions.keyysafe.settings.domain.models.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val sharedPrefs: PreferencesManager,
) : ViewModel() {

    var isAuthSetup by mutableStateOf(false)
    var shouldOpenAuthScreen by mutableStateOf(false)
    var dataStoreOpenAuthScreen by mutableStateOf(false)
    var navBarColor by mutableStateOf(false)
    var isAllowedToTakeScreenshots by mutableStateOf(false)
    var openNotificationWhileRunning by mutableStateOf(false)
    var isAutoBackupOn by mutableStateOf(false)
    var settingsState by mutableStateOf<SettingsState>(SettingsState())
    var selectedLocaleLang by mutableStateOf("")


    init {
        getTheme()
        auth()
        getShowAuthScreenWhenBackToApp()
        getNavBarColor()
        getAllowedToTakeScreenShots()
        getOpenNotificationWhileAppRunning()
        getCurrentLocaleLang()
        getAutoBackup()
    }


    private fun getAutoBackup() = viewModelScope.launch {
        dataStoreManager.get(DataStoreKeys.isAutoBackupOn).collectLatest {
            if (it != null){
                isAutoBackupOn = it
            }else{
                dataStoreManager.save(DataStoreKeys.isAutoBackupOn, true)
            }
        }
    }

    fun auth() {
        val masterPassword = sharedPrefs.getString(PreferencesKeys.MASTER_PASSWORD_KEY, null)
        isAuthSetup = masterPassword != null
    }

    private fun getCurrentLocaleLang() = viewModelScope.launch {
        dataStoreManager.get(DataStoreKeys.AppLanguage).collectLatest {
            if (it != null){
                selectedLocaleLang = it
            }else{
                dataStoreManager.save(DataStoreKeys.AppLanguage, "en")
            }
        }
    }


    private fun getTheme() = viewModelScope.launch {
        dataStoreManager.get(DataStoreKeys.AppTheme).collectLatest {
            if (it != null) {
                settingsState = SettingsState(toAppThemeEnum(it))
            }else{
                dataStoreManager.save(DataStoreKeys.AppTheme, AppTheme.System.name)
            }
        }
    }

    private fun getOpenNotificationWhileAppRunning() = viewModelScope.launch {
        dataStoreManager.get(DataStoreKeys.Notification).collectLatest {
            if (it != null) {
                openNotificationWhileRunning = it
            }else{
                dataStoreManager.save(DataStoreKeys.Notification, false)
            }
        }
    }

    private fun getNavBarColor() = viewModelScope.launch {
        dataStoreManager.get(DataStoreKeys.NavBarColor).collectLatest {
            if (it != null){
                navBarColor = it
            }else{
                dataStoreManager.save(DataStoreKeys.NavBarColor, SettingsRules.navBarColorInitialState)
            }
        }
    }

    private fun getShowAuthScreenWhenBackToApp() = viewModelScope.launch {
        dataStoreManager.get(DataStoreKeys.AuthenticationScreen).collectLatest {
            if (it != null){
                dataStoreOpenAuthScreen = it
            }else{
                dataStoreManager.save(DataStoreKeys.AuthenticationScreen, SettingsRules.showAuthScreenWhenBackToAppInitialState)
            }
        }
    }


    private fun getAllowedToTakeScreenShots() = viewModelScope.launch {
        dataStoreManager.get(DataStoreKeys.AllowScreenShots).collectLatest {
            Log.e("isAllowedToTakeSS", "$it")
            if (it != null){
                isAllowedToTakeScreenshots = it
            }else{
                dataStoreManager.save(DataStoreKeys.AllowScreenShots, SettingsRules.allowedToTakeScreenShots)
            }
        }
    }


}