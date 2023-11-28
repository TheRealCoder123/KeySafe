package com.nextsolutions.keyysafe.main.domain.model

data class PieChartResult(
    val pieChartData: List<Double> = emptyList(),
    val strongPasswords: Int = 0,
    val mediumPasswords: Int = 0,
    val weakPasswords: Int = 0,
    val totalPasswords: Int = 0
)