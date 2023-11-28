package com.nextsolutions.keyysafe.common.util

enum class AppTheme {
    System, Dark, Light
}

fun toAppThemeEnum(appTheme: String) : AppTheme {
    return AppTheme.valueOf(appTheme)
}