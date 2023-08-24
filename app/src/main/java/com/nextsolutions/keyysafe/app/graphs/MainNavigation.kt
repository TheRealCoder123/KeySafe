package com.nextsolutions.keyysafe.app.graphs

sealed class MainNavigation(val route: String) {
    object AuthScreen : MainNavigation("auth_screen")
    object Dashboard : MainNavigation("dashboard_screen")
}