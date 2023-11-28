package com.nextsolutions.keyysafe.settings.ui.Appearance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreKeys
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreManager
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesManager
import com.nextsolutions.keyysafe.common.util.AppTheme
import com.nextsolutions.keyysafe.common.util.toAppThemeEnum
import com.nextsolutions.keyysafe.settings.ui.Appearance.Language.LanguageConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppearanceViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val sharedPreferencesManager: PreferencesManager
) : ViewModel() {

    var isAppThemeDialogVisible by mutableStateOf(false)
    var isAppLanguageDialogVisible by mutableStateOf(false)
    var currentThemeSelected by mutableStateOf<AppTheme?>(null)
    var currentAppLanguageSelected by mutableStateOf<String>("")

    var showArchivedAndTrashEntriesInDashboard by mutableStateOf(false)
    var openLabelsOnStart by mutableStateOf(false)
    var showNotification by mutableStateOf(false)
    var navBarColor by mutableStateOf(false)
    var openAuthScreenWhenBackToApp by mutableStateOf(false)

    init {
        getCurrentTheme()
        getOpenAuthScreenWhenBackToApp()
        getNotification()
        getNavBarColor()
        getNewLocale()
    }

    fun changeTheme(theme: AppTheme) = viewModelScope.launch {
        dataStoreManager.save(DataStoreKeys.AppTheme, theme.name)
    }

    fun getNewLocale() {
        sharedPreferencesManager.getString(LanguageConfig.SELECTED_LANGUAGE_KEY, "en").let {
            currentAppLanguageSelected = it ?: "en"
        }
    }

    fun changeLocale(locale: String) = viewModelScope.launch {
        sharedPreferencesManager.saveString(LanguageConfig.SELECTED_LANGUAGE_KEY, locale)
    }




    fun showNotification() = viewModelScope.launch {
        dataStoreManager.save(DataStoreKeys.Notification, showNotification)
    }

    fun openAuthOnStart() = viewModelScope.launch {
        dataStoreManager.save(DataStoreKeys.AuthenticationScreen, openAuthScreenWhenBackToApp)
    }

    fun navBarColor() = viewModelScope.launch {
        dataStoreManager.save(DataStoreKeys.NavBarColor, navBarColor)
    }



    private fun getCurrentTheme() = viewModelScope.launch {
        dataStoreManager.get(DataStoreKeys.AppTheme).collectLatest {
            if (it != null){
                currentThemeSelected = toAppThemeEnum(it)
            }
        }
    }


    private fun getOpenAuthScreenWhenBackToApp() = viewModelScope.launch {
        dataStoreManager.get(DataStoreKeys.AuthenticationScreen).collectLatest {
            if (it != null){
                openAuthScreenWhenBackToApp = it
            }
        }
    }

    private fun getNotification() = viewModelScope.launch {
        dataStoreManager.get(DataStoreKeys.Notification).collectLatest {
            if (it != null){
                showNotification = it
            }
        }
    }

    private fun getNavBarColor() = viewModelScope.launch {
        dataStoreManager.get(DataStoreKeys.NavBarColor).collectLatest {
            if (it != null){
                navBarColor = it
            }
        }
    }


}