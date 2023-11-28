package com.nextsolutions.keyysafe.main.domain.model

data class CircleBarResult(
    val percentage: Float = 0f,
    val totalEntries: Int = 0,
    val showColorsCloseToEnd: Boolean = false,
    val dontCapPercentagesToInts: Boolean = false
)
