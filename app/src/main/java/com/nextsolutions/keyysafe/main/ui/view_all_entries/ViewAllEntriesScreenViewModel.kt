package com.nextsolutions.keyysafe.main.ui.view_all_entries

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.common.util.ArgumentKeys
import com.nextsolutions.keyysafe.db.entities.Entry
import com.nextsolutions.keyysafe.main.data.mapper.toDashboardEntry
import com.nextsolutions.keyysafe.main.domain.model.DashboardEntry
import com.nextsolutions.keyysafe.main.domain.repository.DashboardEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel()
class ViewAllEntriesScreenViewModel @Inject constructor(
    private val dashboardEntryRepository: DashboardEntryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var labelId = MainNavigation.ViewAllEntriesScreen.VIEW_ALL_ENTRIES

    var searchTextFieldState by mutableStateOf("")
    var entries by mutableStateOf<List<DashboardEntry>>(emptyList())


    init {
        savedStateHandle.get<String>(ArgumentKeys.LABEL_ID_TO_VIEW_ALL_ENTRIES_SCREEN)?.let {
            labelId = it.toInt()
            getEntries()
        }
    }

    fun search(query: String){

        if (query.isNotEmpty()){
            val searchEntries = arrayListOf<DashboardEntry>()

            entries.forEach {
                if (it.title.contains(query, ignoreCase = true)){
                    searchEntries.add(it)
                }
            }

            entries = searchEntries
        }else{
            getEntries()
        }

    }

    private fun getEntries() = viewModelScope.launch {
        if (labelId == MainNavigation.ViewAllEntriesScreen.VIEW_ALL_ENTRIES){
            dashboardEntryRepository.getEntries().collectLatest { enttryList ->
                entries = enttryList.map { it.toDashboardEntry() }
            }
        }else{
            dashboardEntryRepository.getEntriesByLabel(labelId).collectLatest { enttryList ->
                entries = enttryList.map { it.toDashboardEntry() }
            }
        }

    }




}