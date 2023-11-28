package com.nextsolutions.keyysafe.entry.ui.CreateEditEntry

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.common.util.ArgumentKeys
import com.nextsolutions.keyysafe.common.util.IdentifierGenerator
import com.nextsolutions.keyysafe.db.entities.Entry
import com.nextsolutions.keyysafe.db.entities.Label
import com.nextsolutions.keyysafe.entry.domain.enums.EntryDataFieldType
import com.nextsolutions.keyysafe.entry.domain.model.EntryDataField
import com.nextsolutions.keyysafe.entry.domain.model.EntryFiles
import com.nextsolutions.keyysafe.entry.domain.repository.CreateEditRepository
import com.nextsolutions.keyysafe.labels.domain.repository.LabelRepository
import com.nextsolutions.keyysafe.trash.ui.TrashScreen.TrashRules
import com.nextsolutions.keyysafe.ui.theme.entryColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEditViewModel @Inject constructor(
    private val repository: CreateEditRepository,
    private val labelRepository: LabelRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var labels by mutableStateOf<List<Label>>(emptyList())

    var entryId = "null"


    var timeStamp = System.currentTimeMillis()

    var entryTitleTextFieldState by mutableStateOf("")
    var labelId by mutableStateOf(0)
    var entryColor by mutableStateOf(entryColors().random().toArgb())
    val fields = mutableStateOf<List<EntryDataField>>(emptyList())
    val files = mutableStateOf<List<EntryFiles>>(emptyList())
    var moveToTrashSwitchValue by mutableStateOf(false)
    var archiveSwitchValue by mutableStateOf(false)
    var askForAuthenticationSwitchValue by mutableStateOf(false)
    var isTrashFull by mutableStateOf(false)

    var generatedPassword by mutableStateOf("")
    var hasNavigated  by mutableStateOf(false)

    var showUndoButton by mutableStateOf(false)

    var deletedFieldNumb by mutableStateOf(0)

    var currentlyFocusedField by mutableStateOf<EntryDataField?>(null)
    var fieldTextState by mutableStateOf("")

    private var deletedFieldsWithPosition: MutableList<Pair<Int, EntryDataField>> = mutableListOf()


    init {
        isTrashFull()
        getLabels()
        savedStateHandle.get<String>(ArgumentKeys.ENTRY_ID_TO_EDIT_CREATE_ENTRY_SCREEN_KEY)?.let {
            Log.e("entry id", it)
            if (it != "null"){
                entryId = it
                getEntry(entryId)
            }else{
                fields.value = Entry.requiredFields
            }
        }

    }


    fun updatePasswordField(newPassword: String) {

        if (newPassword.isNotEmpty()){
            val oldPasswordField = fields.value.find { it.type == EntryDataFieldType.Password }
            val oldPasswordFieldIndex = fields.value.indexOf(oldPasswordField)

            val newPasswordField = EntryDataField(
                oldPasswordField?.id ?: "",
                oldPasswordField?.type ?: EntryDataFieldType.Password,
                oldPasswordField?.title ?: "",
                newPassword
            )

            val updatedFields = fields.value.toMutableList()
            updatedFields[oldPasswordFieldIndex] = newPasswordField
            fields.value = updatedFields
        }

    }

    fun updateFieldValue() {
        if (currentlyFocusedField != null){
            val oldFieldIndex = fields.value.indexOfFirst { it.id == currentlyFocusedField?.id }
            val newField = EntryDataField(
                currentlyFocusedField?.id ?: "",
                currentlyFocusedField?.type ?: EntryDataFieldType.Account,
                currentlyFocusedField?.title ?: "",
                fieldTextState
            )
            if (oldFieldIndex != -1) {
                val updatedFields = fields.value.toMutableList()
                updatedFields[oldFieldIndex] = newField
                fields.value = updatedFields
            }
        }
    }
    fun save() {
        Log.e("entry save id", entryId)
        if (entryId != "null"){
            saveEntry(entryId)
        }else{
            createEntry()
        }
    }

    private fun getLabels() = viewModelScope.launch {
        labelRepository.getLabels().collectLatest {
            labels = it
        }
    }

    private fun isTrashFull() = viewModelScope.launch {
        repository.getAllEntriesInTrash().collectLatest {
            isTrashFull = it.size >= TrashRules.MAXIMUM_SPACE
        }
    }



    fun addNewField(newField: EntryDataField){
        val updatedFields = fields.value.toMutableList()
        updatedFields.add(newField)
        fields.value = updatedFields
    }


    fun removeField(field: EntryDataField) {
        val updatedFields = fields.value.toMutableList()
        val indexOfField = updatedFields.indexOf(field)

        if (!deletedFieldsWithPosition.any { it.second == field }) {
            deletedFieldsWithPosition.add(indexOfField to field)
        }

        updatedFields.remove(field)
        fields.value = updatedFields

        val numDeletionsCanBeUndone = deletedFieldsWithPosition.size
        showUndoButton = true
        deletedFieldNumb = numDeletionsCanBeUndone
    }

    fun undo() {
        if (deletedFieldsWithPosition.isNotEmpty()) {
            val updatedFields = fields.value.toMutableList()
            for ((position, deletedField) in deletedFieldsWithPosition.reversed()) {
                updatedFields.add(position, deletedField)
            }
            fields.value = updatedFields
            deletedFieldsWithPosition.clear()
            showUndoButton = false
        }
    }



    fun moveToTrash(isTrashed: Boolean) = viewModelScope.launch {
        if (entryId != "null" && !isTrashFull){
            repository.moveToTrash(isTrashed, entryId)
        }else{
            moveToTrashSwitchValue = false
        }
    }

    fun archive(isArchived: Boolean) = viewModelScope.launch {
        if (entryId != "null"){
            repository.archiveEntry(isArchived, entryId)
        }
    }

    fun changeAskForAuth(askForAuth: Boolean) = viewModelScope.launch {
        if (entryId != "null"){
            repository.askForAuth(askForAuth, entryId)
        }
    }

    private fun createEntry() = viewModelScope.launch {
        entryId = IdentifierGenerator.generateUUID()
        val entry = Entry(
            entryId,
            entryTitleTextFieldState,
            fields.value,
            files.value,
            labelId,
            entryColor,
            moveToTrashSwitchValue,
            archiveSwitchValue,
            timeStamp,
            askForAuthenticationSwitchValue
        )
        repository.insertEntry(entry)
    }

    private fun saveEntry(entryId: String) = viewModelScope.launch {
        val entry = Entry(
            entryId,
            entryTitleTextFieldState,
            fields.value,
            files.value,
            labelId,
            entryColor,
            moveToTrashSwitchValue,
            archiveSwitchValue,
            timeStamp,
            askForAuthenticationSwitchValue
        )
        repository.saveEntry(entry)
    }

    private fun getEntry(id: String) = viewModelScope.launch {
        repository.getEntryWithoutFlow(id).let {
            entryId = it.id
            entryTitleTextFieldState = it.title
            entryColor = it.color
            timeStamp = it.timeStamp
            labelId = it.labelId
            fields.value = it.fields
            files.value = it.files
            moveToTrashSwitchValue = it.trashed
            archiveSwitchValue = it.archived
            askForAuthenticationSwitchValue = it.askForAuth
        }
    }



}