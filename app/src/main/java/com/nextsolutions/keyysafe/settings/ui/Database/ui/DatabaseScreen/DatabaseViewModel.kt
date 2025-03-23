package com.nextsolutions.keyysafe.settings.ui.Database.ui.DatabaseScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreKeys
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreManager
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.BackUpResponse
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.RestoreResponse
import com.nextsolutions.keyysafe.settings.ui.Database.domain.enums.AuthenticateFor
import com.nextsolutions.keyysafe.settings.ui.Database.domain.repository.BackUpRepository
import com.nextsolutions.keyysafe.settings.ui.Database.domain.repository.RestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val restoreRepository: RestoreRepository,
    private val backUpRepository: BackUpRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {



    var autoBackUp by mutableStateOf(false)
    var fileUri by mutableStateOf("")
    var isBackupNameDialogVisible by mutableStateOf(false)
    var isAuthenticated by mutableStateOf(false)
    var hasNavigated by mutableStateOf(false)
    var authenticateFor by mutableStateOf(AuthenticateFor.BACK_UP_DATABASE)

    var restoreStateResponse by mutableStateOf(RestoreResponse())
    var backupStateResponse by mutableStateOf(BackUpResponse())


    init {
        getAutoBackUp()
    }

    fun setAutoBack() = viewModelScope.launch {
        dataStoreManager.save(DataStoreKeys.isAutoBackupOn, autoBackUp)
    }

    private fun getAutoBackUp() = viewModelScope.launch {
        dataStoreManager.get(DataStoreKeys.isAutoBackupOn).collectLatest {
            it?.let { isOn ->
                autoBackUp = isOn
            }
        }
    }


    fun restore(filePass: String) = viewModelScope.launch {
        restoreStateResponse = restoreRepository.restore(fileUri.toUri(), filePass)
        if (restoreStateResponse.success){
            restoreRepository.saveDataInDatabase(restoreStateResponse.backupData)
        }
        Log.e("result", "$restoreStateResponse")
    }

    fun backup(fileName: String, filePass: String) = viewModelScope.launch {
        backupStateResponse = backUpRepository.export(fileName, filePass)
        Log.e("result backup", "$backupStateResponse")
    }

}