package com.nextsolutions.keyysafe.analytics.ui.analytics_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.analytics.domain.repository.AnalyticsRepository
import com.nextsolutions.keyysafe.main.data.mapper.toDashboardEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsScreenViewModel @Inject constructor(
    private val analyticsRepository: AnalyticsRepository
) : ViewModel() {

    companion object {
        val NO_ENTRY_SELECTED = null
    }

    private val _state = mutableStateOf(AnalyticsScreenState())
    var state: State<AnalyticsScreenState> = _state


    init {
        getEntries()
    }

    fun selectEntry(entryId: String) = viewModelScope.launch {
        analyticsRepository.getEntryById(entryId).collectLatest {
            _state.value = _state.value.copy(checkByEntry = it)
        }
    }


    fun unSelectEntry() {
        _state.value = _state.value.copy(checkByEntry = NO_ENTRY_SELECTED)
    }

    private fun getEntries() = viewModelScope.launch {
        analyticsRepository.getEntries().collectLatest { entries ->
            val mappedEntries = entries.map { entry -> entry.toDashboardEntry() }
            _state.value = _state.value.copy(entries = mappedEntries)
        }
    }



}