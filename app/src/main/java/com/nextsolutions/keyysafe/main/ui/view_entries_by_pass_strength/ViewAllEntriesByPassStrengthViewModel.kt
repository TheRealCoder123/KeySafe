package com.nextsolutions.keyysafe.main.ui.view_entries_by_pass_strength

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker
import com.nextsolutions.keyysafe.common.util.ArgumentKeys
import com.nextsolutions.keyysafe.common.util.Constants.NO_LABEL
import com.nextsolutions.keyysafe.main.data.mapper.toDashboardEntry
import com.nextsolutions.keyysafe.main.domain.model.DashboardEntry
import com.nextsolutions.keyysafe.main.domain.repository.DashboardEntryRepository
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.Orange
import com.nextsolutions.keyysafe.ui.theme.Red
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAllEntriesByPassStrengthViewModel @Inject constructor (
    private val dashboardEntryRepository: DashboardEntryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    var searchTextFieldState by mutableStateOf("")
    var byStrength by mutableStateOf(PasswordChecker.PasswordStrength.STRONG)
    var entries by mutableStateOf<List<DashboardEntry>>(emptyList())
    var topBarsColor by mutableStateOf<Color>(Green)
    var labelIdArg by mutableStateOf(0)

    init {

        val byStrengthArg = savedStateHandle.get<String>(ArgumentKeys.PASSWORD_STRENGTH_TO_VIEW_ENTRIES_BY_PASSWORD_STRENGTH)
        val labelIdArg = savedStateHandle.get<String>(ArgumentKeys.LABEL_ID_TO_VIEW_ENTRIES_BY_STRENGTH_SCREEN)?.toInt()

        if (byStrengthArg != null && labelIdArg != null){
            this.labelIdArg = labelIdArg
            val passwordStrength = PasswordChecker.getPasswordStrengthByString(byStrengthArg)
            byStrength = passwordStrength
            topBarsColor = when(byStrength){
                PasswordChecker.PasswordStrength.STRONG -> Green
                PasswordChecker.PasswordStrength.MEDIUM -> Orange
                PasswordChecker.PasswordStrength.WEAK -> Red
            }
            getEntries()
        }

    }

    fun search(query: String){

        if (query.isNotEmpty()){
            val searchEntries = arrayListOf<DashboardEntry>()

            entries.forEach {
                if (it.title.contains(query.trim(), ignoreCase = true)){
                    searchEntries.add(it)
                }
            }

            entries = searchEntries
        }else{
            getEntries()
        }

    }

    private fun getEntries() = viewModelScope.launch {
        if(labelIdArg != NO_LABEL){
            dashboardEntryRepository.getEntriesByLabel(labelIdArg).collectLatest {
                dashboardEntryRepository.getEntriesByStrength(it, byStrength).let { byStrengthEntries ->
                    entries = byStrengthEntries .map { mappingEntry -> mappingEntry.toDashboardEntry() }
                }
            }
        }else{
            dashboardEntryRepository.getEntries().collectLatest {
                dashboardEntryRepository.getEntriesByStrength(it, byStrength).let { byStrengthEntries ->
                    entries = byStrengthEntries .map { mappingEntry -> mappingEntry.toDashboardEntry() }
                }
            }
        }
    }

}