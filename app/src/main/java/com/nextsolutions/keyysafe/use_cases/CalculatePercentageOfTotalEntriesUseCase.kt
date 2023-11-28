package com.nextsolutions.keyysafe.use_cases

class CalculatePercentageOfTotalEntriesUseCase {
    fun calculate(
        entries: Int,
        totalEntries: Int
    ): Float {
        val totalPercentages = (entries.toFloat() / totalEntries.toFloat()) * 100
        return if (totalPercentages.isNaN()) 0f else totalPercentages
    }
}