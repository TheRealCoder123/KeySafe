package com.nextsolutions.keyysafe.use_cases

import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker
import com.nextsolutions.keyysafe.db.entities.Entry
import com.nextsolutions.keyysafe.entry.domain.enums.EntryDataFieldType
import com.nextsolutions.keyysafe.main.domain.model.PieChartResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PasswordAnalyzerUseCase {

    suspend fun analyze(
        entries: List<Entry>
    ): PieChartResult {
        return withContext(Dispatchers.IO) {



            if (entries.isEmpty()){
                 PieChartResult()
            }

            val passwordStrengthChecker = PasswordChecker()
            val allPasswords = arrayListOf<String>()

            entries.forEach { entry ->
                entry.fields.forEach { field ->
                    if (field.type == EntryDataFieldType.Password && field.value.isNotEmpty()) {
                        allPasswords.add(field.value)
                    }
                }
            }

            if (entries.isEmpty() || allPasswords.isEmpty()){
                PieChartResult()
            }


            val percentages = arrayListOf<Double>()

            var strongCount = 0
            var mediumCount = 0
            var weakCount = 0

            for (password in allPasswords) {
                when (passwordStrengthChecker.checkPasswordStrength(password)) {
                    PasswordChecker.PasswordStrength.STRONG -> strongCount++
                    PasswordChecker.PasswordStrength.MEDIUM -> mediumCount++
                    PasswordChecker.PasswordStrength.WEAK -> weakCount++
                }
            }

            val totalPasswords = allPasswords.size
            val strongPercentage = (strongCount.toDouble() / totalPasswords) * 100
            val mediumPercentage = (mediumCount.toDouble() / totalPasswords) * 100
            val weakPercentage = (weakCount.toDouble() / totalPasswords) * 100

            val totalPercentage = strongPercentage + mediumPercentage + weakPercentage
            val strongNormalized = (strongPercentage / totalPercentage) * 100
            val mediumNormalized = (mediumPercentage / totalPercentage) * 100
            val weakNormalized = (weakPercentage / totalPercentage) * 100

            percentages.add(strongNormalized)
            percentages.add(mediumNormalized)
            percentages.add(weakNormalized)

            PieChartResult(
                percentages,
                strongCount,
                mediumCount,
                weakCount,
                totalPasswords
            )
        }
    }

}