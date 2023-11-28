package com.nextsolutions.keyysafe.main.ui.password_generator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker
import com.nextsolutions.keyysafe.common.util.ArgumentKeys
import com.nextsolutions.keyysafe.main.domain.use_cases.PasswordGeneratorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordGeneratorViewModel @Inject constructor(
    private val generatePasswordGeneratorUseCase: PasswordGeneratorUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var generatedPasswordTextState by mutableStateOf("")
    var sliderPosition by mutableStateOf(8f)


    var useCapitalLetters by mutableStateOf(true)
    var useDigits by mutableStateOf(true)
    var useSymbols by mutableStateOf(true)

    var shouldSendBackResult by mutableStateOf(true)

    var areUseNumbersAndSymbolsEnabled by mutableStateOf(true)

    var passwordStrength by mutableStateOf(PasswordChecker.PasswordStrength.WEAK)

    init {
        val shouldSendBackResult = savedStateHandle.get<String>(ArgumentKeys.SHOULD_PASS_GENERATED_PASSWORD_BACK)
        if (shouldSendBackResult != null){
            this.shouldSendBackResult = shouldSendBackResult.toBoolean()
        }
    }

    fun checkPassword() = viewModelScope.launch {
        PasswordChecker().checkPasswordStrength(generatedPasswordTextState).let {
            passwordStrength = it
        }
    }

    fun generatePassword() = viewModelScope.launch {
        generatePasswordGeneratorUseCase.generate(
            sliderPosition.toInt(),
            useCapitalLetters,
            useDigits,
            useSymbols
        ).let {
            generatedPasswordTextState = it.trim()
            checkPassword()
        }
    }

}