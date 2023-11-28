package com.nextsolutions.keyysafe.app.graphs

sealed class Navigation(val route: String){
    object AuthSetupNavigation : Navigation("auth_setup_navigation")
    object MainNavigation : Navigation("main_navigation_navigation")
    object SettingsNavigation : Navigation("settings_navigation")
    object FAQScreen : Navigation("faq_screen")

}
