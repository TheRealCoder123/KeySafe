package com.nextsolutions.keyysafe.labels.ui.EditLabelsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.db.entities.Label
import com.nextsolutions.keyysafe.labels.domain.repository.LabelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditLabelsViewModel @Inject constructor(
    private val labelRepository: LabelRepository
) : ViewModel() {

    var labels by mutableStateOf<List<Label>>(emptyList())
    var labelTitleTextState by mutableStateOf("")
    var editLabelTitleTextState by mutableStateOf("")
    var selectedLabel by mutableStateOf<Label?>(null)

    var isLabelOptionsDialogVisible by mutableStateOf(false)
    var isEditLabelDialogVisible by mutableStateOf(false)

    init {
        viewModelScope.launch {
            labelRepository.getLabels().collectLatest {
                labels = it
            }
        }
    }

    fun createLabel(label: Label) = viewModelScope.launch {
        labelRepository.insertLabel(label)
        labelTitleTextState = ""
    }

    fun deleteLabel() = viewModelScope.launch {
        if (selectedLabel != null){
            val deleteLabelId = selectedLabel?.id ?: 0
            labelRepository.deleteLabel(deleteLabelId)
            labelRepository.deleteConnectionsToLabel(deleteLabelId)
            selectedLabel = null
            isLabelOptionsDialogVisible = false
        }
    }


    fun updateLabel(newTitle: String, labelId: Int) = viewModelScope.launch {
        labelRepository.updateLabelTitle(newTitle, labelId)
        isEditLabelDialogVisible = false
        isLabelOptionsDialogVisible = false
        selectedLabel = null
    }


}