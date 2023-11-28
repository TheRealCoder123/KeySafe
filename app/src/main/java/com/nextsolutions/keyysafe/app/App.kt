package com.nextsolutions.keyysafe.app

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.auth.domain.enums.AuthScreenUsability
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App(
    mainViewModel: MainViewModel
) {
    KeySafeTheme(
        appTheme = mainViewModel.settingsState.theme,
        showNavBarColor = mainViewModel.navBarColor
    ) {
        val navHostController = rememberAnimatedNavController()


        if (mainViewModel.shouldOpenAuthScreen &&
            mainViewModel.dataStoreOpenAuthScreen &&
            navHostController.currentDestination?.route != MainNavigation.AuthScreen.passScreenUsability(AuthScreenUsability.RE_AUTHENTICATE)
            && mainViewModel.isAuthSetup
            ){
            navHostController.navigate(MainNavigation.AuthScreen.passScreenUsability(AuthScreenUsability.RE_AUTHENTICATE))
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(KeySafeTheme.colors.background),
        ) {
            MainNavigationGraph(
                navHostController = navHostController,
                isAuthSetup =  mainViewModel.isAuthSetup,
                mainViewModel = mainViewModel
            )
        }

    }
}