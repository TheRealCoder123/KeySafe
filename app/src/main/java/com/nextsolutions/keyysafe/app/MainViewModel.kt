package com.nextsolutions.keyysafe.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreKeys
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreManager
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesKeys
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val sharedPrefs: PreferencesManager
) : ViewModel() {

    var isAuthSetup by mutableStateOf(false)

    init {
        val masterPassword = sharedPrefs.getString(PreferencesKeys.MASTER_PASSWORD_KEY, null)
        isAuthSetup = masterPassword != null
    }

}