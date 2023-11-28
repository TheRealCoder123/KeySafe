package com.nextsolutions.keyysafe.main.ui.dashboard_2_0

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.labels.domain.repository.LabelRepository
import com.nextsolutions.keyysafe.main.data.mapper.toDashboardEntry
import com.nextsolutions.keyysafe.main.domain.repository.DashboardEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel()
class DashboardViewModel @Inject constructor(
    private val dashboardEntryRepository: DashboardEntryRepository,
    private val labelRepository: LabelRepository
) : ViewModel() {

    companion object {
        const val NO_SELECTED_LABEL = -1
    }

    private val _state = mutableStateOf(DashboardState())
    val state: State<DashboardState> = _state

    init {
        getEntries(NO_SELECTED_LABEL)
        getLabels()
    }

    fun getEntries(labelId: Int) = viewModelScope.launch {
        _state.value = _state.value.copy(selectedLabelId = labelId)
        if (labelId != NO_SELECTED_LABEL){
            dashboardEntryRepository.getRecentEntriesByLabel(labelId).collectLatest { entries ->
                val dashboardEntries = entries.map { it.toDashboardEntry() }
                _state.value = _state.value.copy(entries = dashboardEntries)
            }
        }else{
            dashboardEntryRepository.getRecentEntries().collectLatest { entries ->
                val dashboardEntries = entries.map { it.toDashboardEntry() }
                _state.value = _state.value.copy(entries = dashboardEntries)
            }
        }

    }

    private fun getLabels() = viewModelScope.launch {
        labelRepository.getLabels().collectLatest { labels ->
            _state.value = _state.value.copy(labels = labels)
        }
    }


}