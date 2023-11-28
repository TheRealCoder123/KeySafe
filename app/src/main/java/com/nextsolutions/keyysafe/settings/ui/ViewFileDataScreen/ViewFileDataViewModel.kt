package com.nextsolutions.keyysafe.settings.ui.ViewFileDataScreen

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.common.util.ArgumentKeys
import com.nextsolutions.keyysafe.db.entities.Label
import com.nextsolutions.keyysafe.main.data.mapper.toDashboardEntry
import com.nextsolutions.keyysafe.main.domain.model.DashboardEntry
import com.nextsolutions.keyysafe.main.ui.dashboard.DashboardViewModel
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.BACKUP_DATA_PASSWORD
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.RestoreResponse
import com.nextsolutions.keyysafe.settings.ui.Database.domain.repository.RestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewFileDataViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val restoreRepository: RestoreRepository
): ViewModel() {

    var selectedLabel by mutableStateOf<Label?>(null)
    var restoreStateResponse by mutableStateOf(RestoreResponse())
    var isRestoreClicked by mutableStateOf(false)

    var entries by mutableStateOf<List<DashboardEntry>>(emptyList())
    var archivedEntries by mutableStateOf<List<DashboardEntry>>(emptyList())
    var trashedEntries by mutableStateOf<List<DashboardEntry>>(emptyList())

   init {
       val fileUri = savedStateHandle.get<String>(ArgumentKeys.FILE_URI_TO_VIEW_FILE_DATA)
       if (fileUri != null){
           viewModelScope.launch {
               delay(1000)
               restoreStateResponse =  restoreRepository.restore(Uri.parse(fileUri.replace('|','%')), BACKUP_DATA_PASSWORD)
               if (restoreStateResponse.success){
                   getEntriesBy()
               }
           }
       }

   }


    fun restore() = viewModelScope.launch {
        if (restoreStateResponse.success){
            restoreRepository.saveDataInDatabase(restoreStateResponse.backupData)
            isRestoreClicked = true
        }
        Log.e("result", "$restoreStateResponse")
    }

    fun getEntriesBy() {

        restoreStateResponse.backupData.entries.let {
            if (selectedLabel != DashboardViewModel.NO_SELECTED_LABEL){
                entries = it.filter { entry -> !entry.trashed && !entry.archived && entry.labelId == (selectedLabel?.id ?: 0) }.map { entry -> entry.toDashboardEntry() }
                archivedEntries = it.filter { entry -> entry.archived && entry.labelId == (selectedLabel?.id ?: 0) }.map { entry -> entry.toDashboardEntry() }
                trashedEntries = it.filter { entry -> entry.trashed && entry.labelId == (selectedLabel?.id ?: 0) }.map { entry -> entry.toDashboardEntry() }
            }else{
                entries = it.filter { entry -> !entry.trashed && !entry.archived }.map { entry -> entry.toDashboardEntry() }
                archivedEntries = it.filter { entry -> entry.archived }.map { entry -> entry.toDashboardEntry() }
                trashedEntries = it.filter { entry -> entry.trashed }.map { entry -> entry.toDashboardEntry() }
            }
        }


    }


}