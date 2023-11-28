package com.nextsolutions.keyysafe.trash.ui.TrashScreen

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.db.entities.Entry
import com.nextsolutions.keyysafe.main.data.mapper.toDashboardEntry
import com.nextsolutions.keyysafe.main.domain.model.CircleBarResult
import com.nextsolutions.keyysafe.main.domain.model.DashboardEntry
import com.nextsolutions.keyysafe.trash.domain.models.WarningData
import com.nextsolutions.keyysafe.trash.domain.repository.TrashRepository
import com.nextsolutions.keyysafe.use_cases.CalculatePercentageOfTotalEntriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrashScreenViewModel @Inject constructor(
    private val trashRepository: TrashRepository,
    private val calculatePercentageOfTotalEntriesUseCase: CalculatePercentageOfTotalEntriesUseCase
) : ViewModel(){

    var searchTextFieldState by mutableStateOf("")
    var isCirclePercentageComponentAnimPlayed by mutableStateOf(false)
    var isTrashEntryDialogVisible by mutableStateOf(false)
    var storageCircleBarResult by mutableStateOf(CircleBarResult())
    var trashedEntriesCircleBarResult by mutableStateOf(CircleBarResult())
    var entries by mutableStateOf<List<DashboardEntry>>(emptyList())
    private var notArchivedEntries by mutableStateOf<List<Entry>>(emptyList())
    var warningData by mutableStateOf<WarningData>(WarningData())
    var selectedEntryId by mutableStateOf<String>("")


    init { init() }

    private fun init() = viewModelScope.launch {
        combine(
            trashRepository.getTrashedEntries(),
            trashRepository.getAllEntriesNotArchived()
        ){ trashedEntries, allEntriesNotArchived ->
            entries = trashedEntries.map { it.toDashboardEntry() }
            notArchivedEntries = allEntriesNotArchived
            calculateStorage()
            calculateTotalEntriesInTrash()
        }.collect()
    }

    private fun calculateStorage() = viewModelScope.launch {
        val storageAvailable = calculatePercentageOfTotalEntriesUseCase.calculate(entries.size, TrashRules.MAXIMUM_SPACE)
        storageCircleBarResult = CircleBarResult(
            storageAvailable, entries.size,
            showColorsCloseToEnd = true,
            dontCapPercentagesToInts = true
        )
        warningData = WarningData(
            storageAvailable > 90f,
            "You are running low on storage",
            "Try deleting some entries"
        )
        warningData = WarningData(
            storageAvailable == 0f,
            "Trash is empty",
            "You do not have any entries in trash.",
            Icons.Default.RestoreFromTrash
        )
    }

    private fun calculateTotalEntriesInTrash() = viewModelScope.launch {
        val totalEntriesInPercentage = calculatePercentageOfTotalEntriesUseCase.calculate(entries.size, notArchivedEntries.size)
        trashedEntriesCircleBarResult = CircleBarResult(totalEntriesInPercentage, entries.size)
    }

    fun deleteEntry() = viewModelScope.launch {
        trashRepository.deleteEntry(selectedEntryId)
        selectedEntryId = ""
    }

    fun removeFromTrash() = viewModelScope.launch {
        trashRepository.removeFromTrash(selectedEntryId)
        selectedEntryId = ""
    }




}