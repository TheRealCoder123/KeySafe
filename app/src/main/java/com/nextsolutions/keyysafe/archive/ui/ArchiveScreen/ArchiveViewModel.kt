package com.nextsolutions.keyysafe.archive.ui.ArchiveScreen

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Unarchive
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.archive.domain.repository.ArchiveRepository
import com.nextsolutions.keyysafe.db.entities.Entry
import com.nextsolutions.keyysafe.main.data.mapper.toDashboardEntry
import com.nextsolutions.keyysafe.main.domain.model.CircleBarResult
import com.nextsolutions.keyysafe.main.domain.model.DashboardEntry
import com.nextsolutions.keyysafe.main.domain.model.PieChartResult
import com.nextsolutions.keyysafe.trash.domain.models.WarningData
import com.nextsolutions.keyysafe.use_cases.CalculatePercentageOfTotalEntriesUseCase
import com.nextsolutions.keyysafe.use_cases.PasswordAnalyzerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    private val archiveRepository: ArchiveRepository,
    private val passwordAnalyzerUseCase: PasswordAnalyzerUseCase,
    private val calculatePercentageOfTotalEntriesUseCase: CalculatePercentageOfTotalEntriesUseCase
) : ViewModel() {

    var searchTextFieldState by mutableStateOf("")
    var pieChartData by mutableStateOf<PieChartResult>(PieChartResult())
    var circleBarData by mutableStateOf<CircleBarResult>(CircleBarResult())
    var entries by mutableStateOf<List<DashboardEntry>>(emptyList())
    var isPieChartAnimPlayed by mutableStateOf(false)
    var isCirclePercentageComponentAnimPlayed by mutableStateOf(false)
    var warningData by mutableStateOf<WarningData>(WarningData())




    init {
        getEntries()
        calculatePercentageOfArchivedEntries()
    }

    private fun getEntries() = viewModelScope.launch {
        archiveRepository.getArchivedEntries().collectLatest { entryList ->
            entries = entryList.map { it.toDashboardEntry() }
            val analysisResult = passwordAnalyzerUseCase.analyze(entryList)
            pieChartData = analysisResult
            warningData = WarningData(
                entryList.isEmpty(),
                "Archive is empty",
                "Your archive folder is empty.",
                icon = Icons.Default.Unarchive
            )
        }
    }

    private fun calculatePercentageOfArchivedEntries() = viewModelScope.launch {
        combine(
            archiveRepository.getArchivedEntries(),
            archiveRepository.getAllEntries()
        ) { entryList, allEntriesList ->
            val percentage = calculatePercentageOfTotalEntriesUseCase.calculate(entryList.size, allEntriesList.size)
            circleBarData = CircleBarResult(
                percentage,
                entryList.size
            )
        }.collect()
    }




}