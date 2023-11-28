package com.nextsolutions.keyysafe.main.ui.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreManager
import com.nextsolutions.keyysafe.db.entities.Label
import com.nextsolutions.keyysafe.labels.domain.repository.LabelRepository
import com.nextsolutions.keyysafe.main.data.mapper.toDashboardEntry
import com.nextsolutions.keyysafe.main.domain.model.DashboardEntry
import com.nextsolutions.keyysafe.main.domain.model.PieChartResult
import com.nextsolutions.keyysafe.main.domain.repository.DashboardEntryRepository
import com.nextsolutions.keyysafe.trash.domain.repository.TrashRepository
import com.nextsolutions.keyysafe.trash.ui.TrashScreen.TrashRules
import com.nextsolutions.keyysafe.use_cases.PasswordAnalyzerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val labelRepository: LabelRepository,
    private val dashboardEntryRepository: DashboardEntryRepository,
    private val trashRepository: TrashRepository,
    private val passwordAnalyzerUseCase: PasswordAnalyzerUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    companion object {
        val NO_SELECTED_LABEL = null
    }

    var isPieChartAnimPlayed by mutableStateOf(false)
    var isTrashEntryDialogVisible by mutableStateOf(false)
    //var isAuthenticated  by mutableStateOf(false)
    var hasNavigated  by mutableStateOf(false)
    var isAutofillServiceDialogVisible  by mutableStateOf(true)
    var isTrashDialogVisible  by mutableStateOf(true)
    var isTrashFull by mutableStateOf(false)
    var selectedEntryId by mutableStateOf("")
    var clickedEntryId by mutableStateOf("")

    var selectedLabel by mutableStateOf<Label?>(null)
    var topBarTitleState by mutableStateOf("")

    var labels by mutableStateOf<List<Label>>(emptyList())
    var entries by mutableStateOf<List<DashboardEntry>>(emptyList())
    var pieChartData by mutableStateOf(PieChartResult())

    init {
        isTrashFull()
        getLabels()
        getEntriesBy(NO_SELECTED_LABEL)
    }


    fun moveToTrash(isTrashed: Boolean, entryId: String) = viewModelScope.launch {
        if (!isTrashFull) {
            dashboardEntryRepository.moveToTrash(isTrashed, entryId)
        }
    }


    fun archive(isArchived: Boolean, entryId: String) = viewModelScope.launch {
        dashboardEntryRepository.archiveEntry(isArchived, entryId)
    }

    private fun isTrashFull() = viewModelScope.launch {
        dashboardEntryRepository.getAllEntriesInTrash().collectLatest {
            isTrashFull = it.size >= TrashRules.MAXIMUM_SPACE
        }
    }

    fun deleteEntry() = viewModelScope.launch {
        trashRepository.deleteEntry(selectedEntryId)
        selectedEntryId = ""
    }

    fun removeFromTrash() = viewModelScope.launch {
        trashRepository.removeFromTrash(selectedEntryId)
        selectedEntryId = ""
    }



    fun getEntriesBy(label: Label?){
        selectedLabel = if (label == NO_SELECTED_LABEL){
            NO_SELECTED_LABEL
        }else{
            label
        }
        getAllEntries()
    }



    private fun getAllEntries() = viewModelScope.launch {
        dashboardEntryRepository.getAllOrderedEntries().collectLatest {

            entries = if (selectedLabel != NO_SELECTED_LABEL){
                it.filter { entry -> !entry.trashed && !entry.archived && entry.labelId == (selectedLabel?.id ?: 0) }.map { entry -> entry.toDashboardEntry() }.take(20)
            }else{
                it.filter { entry -> !entry.trashed && !entry.archived }.map { entry -> entry.toDashboardEntry() }.take(20)
            }

            pieChartData = if (selectedLabel != NO_SELECTED_LABEL) {
                val labelEntries = it.filter { entry -> entry.labelId == (selectedLabel?.id ?: 0) && !entry.trashed && !entry.archived }
                val analysisResult = passwordAnalyzerUseCase.analyze(labelEntries)
                analysisResult
            }else{
                val allEntries = it.filter { entry -> !entry.trashed && !entry.archived }
                val analysisResult = passwordAnalyzerUseCase.analyze(allEntries)
                analysisResult
            }
        }
    }

    private fun getLabels() {
        viewModelScope.launch {
            labelRepository.getLabels().collectLatest {
                labels = it
            }
        }
    }


}