package com.nextsolutions.keyysafe.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.app.graphs.Navigation
import com.nextsolutions.keyysafe.app.graphs.SettingsNavigation
import com.nextsolutions.keyysafe.app.graphs.SetupNavigation
import com.nextsolutions.keyysafe.auth.ui.AuthScreen.AuthScreen
import com.nextsolutions.keyysafe.auth.ui.AuthSetupScreen.AuthSetupScreen
import com.nextsolutions.keyysafe.onboarding.OnBoardingScreen

@Composable
fun MainNavigationGraph(navHostController: NavHostController, isAuthSetup: Boolean) {
    NavHost(
        navController = navHostController,
        startDestination = if (isAuthSetup) Navigation.MainNavigation.route else Navigation.AuthSetupNavigation.route
    ){

        navigation(
            route = Navigation.AuthSetupNavigation.route,
            startDestination = SetupNavigation.AuthSetupScreen.route
        ){
            composable(SetupNavigation.AuthSetupScreen.route){
                AuthSetupScreen(navController = navHostController)
            }
            composable(SetupNavigation.OnBoardingScreen.route){
                OnBoardingScreen()
            }
        }

        navigation(
            route = Navigation.MainNavigation.route,
            startDestination = MainNavigation.AuthScreen.route
        ){
            composable(MainNavigation.AuthScreen.route){
                AuthScreen()
            }
            composable(MainNavigation.Dashboard.route){

            }
        }


        navigation(
            route = Navigation.SettingsNavigation.route,
            startDestination = SettingsNavigation.SettingsScreen.route
        ){
            composable(SettingsNavigation.SettingsScreen.route){

            }
        }

    }
}