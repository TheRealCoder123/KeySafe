package com.nextsolutions.keyysafe.app.graphs

sealed class SetupNavigation(val route: String) {
    object AuthSetupScreen : SetupNavigation("auth_setup_screen")
    object OnBoardingScreen : SetupNavigation("on_boarding_screen")
}