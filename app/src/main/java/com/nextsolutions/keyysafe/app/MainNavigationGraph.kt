package com.nextsolutions.keyysafe.app

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.nextsolutions.keyysafe.FAQ.ui.FAQScreen.FAQScreen
import com.nextsolutions.keyysafe.analytics.ui.analytics_screen.AnalyticsScreen
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.app.graphs.Navigation
import com.nextsolutions.keyysafe.app.graphs.SettingsNavigation
import com.nextsolutions.keyysafe.app.graphs.SetupNavigation
import com.nextsolutions.keyysafe.archive.ui.ArchiveScreen.ArchiveScreen
import com.nextsolutions.keyysafe.auth.domain.enums.AuthScreenUsability
import com.nextsolutions.keyysafe.auth.ui.AuthScreen.AuthScreen
import com.nextsolutions.keyysafe.auth.ui.AuthSetupScreen.AuthSetupScreen
import com.nextsolutions.keyysafe.entry.ui.CreateEditEntry.CreateEditEntryScreen
import com.nextsolutions.keyysafe.labels.ui.EditLabelsScreen.EditLabelsScreen
import com.nextsolutions.keyysafe.main.ui.dashboard.Dashboard
import com.nextsolutions.keyysafe.main.ui.password_generator.PasswordGeneratorScreen
import com.nextsolutions.keyysafe.main.ui.view_all_entries.ViewAllEntriesScreen
import com.nextsolutions.keyysafe.main.ui.view_entries_by_pass_strength.ViewAllEntriesByPassStrength
import com.nextsolutions.keyysafe.onboarding.OnBoardingScreen
import com.nextsolutions.keyysafe.settings.ui.About.AboutScreen.AboutScreen
import com.nextsolutions.keyysafe.settings.ui.Appearance.AppearanceScreen
import com.nextsolutions.keyysafe.settings.ui.AutoFill.ui.AutoFillScreen
import com.nextsolutions.keyysafe.settings.ui.Database.ui.DatabaseScreen.DatabaseScreen
import com.nextsolutions.keyysafe.settings.ui.Security.ChangeMasterPasswordScreen.ChangeMasterPasswordScreen
import com.nextsolutions.keyysafe.settings.ui.Security.SecurityScreen.SecurityScreen
import com.nextsolutions.keyysafe.settings.ui.SettingsScreen.SettingsScreen
import com.nextsolutions.keyysafe.settings.ui.ViewFileDataScreen.ViewFileDataScreen
import com.nextsolutions.keyysafe.trash.ui.TrashScreen.TrashScreen

const val ANIMATION_DURATION = 600

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavigationGraph(
    navHostController: NavHostController,
    isAuthSetup: Boolean,
    mainViewModel: MainViewModel
) {

    AnimatedNavHost(
        navController = navHostController,
        startDestination = if (isAuthSetup) Navigation.MainNavigation.route else Navigation.AuthSetupNavigation.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ){

        composable(Navigation.FAQScreen.route){
            FAQScreen(navHostController)
        }

        navigation(
            route = Navigation.AuthSetupNavigation.route,
            startDestination = SetupNavigation.AuthSetupScreen.route
        ){
            composable(SetupNavigation.AuthSetupScreen.route){
                AuthSetupScreen(navController = navHostController)
            }
            composable(SetupNavigation.OnBoardingScreen.route){
                OnBoardingScreen(navHostController)
            }
        }

        navigation(
            route = Navigation.MainNavigation.route,
            startDestination = MainNavigation.AuthScreen.passScreenUsability(AuthScreenUsability.AUTHENTICATE)
        ){
            composable(MainNavigation.AuthScreen.route){
                AuthScreen(navController = navHostController, mainViewModel = mainViewModel)
            }
            composable(MainNavigation.Dashboard.route){
                Dashboard(navController = navHostController)
                //com.nextsolutions.keyysafe.main.ui.dashboard_2_0.Dashboard(navController = navHostController)
            }
            composable(MainNavigation.ArchiveScreen.route){
                ArchiveScreen(navHostController)
            }
            composable(MainNavigation.TrashScreen.route){
                TrashScreen(navHostController)
            }
            composable(MainNavigation.CreateEntryScreen.route){
                CreateEditEntryScreen(navController = navHostController)
            }
            composable(MainNavigation.EditLabelsScreen.route){
                EditLabelsScreen(navHostController)
            }
            composable(MainNavigation.ViewAllEntriesScreen.route){
                 ViewAllEntriesScreen(navHostController)
            }
            composable(MainNavigation.ViewAllEntriesByPassStrengthScreen.route){
                ViewAllEntriesByPassStrength(navController = navHostController)
            }
            composable(MainNavigation.PasswordGeneratorScreen.route){
                PasswordGeneratorScreen(navHostController)
            }
            composable(MainNavigation.AutoFill.route){
                AutoFillScreen(navHostController)
            }
            composable(MainNavigation.AnalyticsScreen.route){
                AnalyticsScreen(navHostController)
            }
        }


        navigation(
            route = Navigation.SettingsNavigation.route,
            startDestination = SettingsNavigation.SettingsScreen.route
        ){
            composable(SettingsNavigation.SettingsScreen.route){
                SettingsScreen(navHostController)
            }
            composable(SettingsNavigation.Appearance.route){
                AppearanceScreen(navHostController)
            }
            composable(SettingsNavigation.Security.route){
                SecurityScreen(navHostController)
            }
            composable(SettingsNavigation.Database.route){
                DatabaseScreen(navHostController, mainViewModel = mainViewModel)
            }
            composable(SettingsNavigation.About.route){
                AboutScreen(navHostController)
            }
            composable(SettingsNavigation.ChangeMasterPassword.route){
                ChangeMasterPasswordScreen(navHostController)
            }
            composable(SettingsNavigation.ViewFileDataScreen.route){
                ViewFileDataScreen(navHostController)
            }
        }

    }
}

val enterTransition = {
    scaleIntoContainer()
}

val exitTransition = {
    scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
}

val popEnterTransition = {
    scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
}

val  popExitTransition = {
    scaleOutOfContainer()
}


enum class ScaleTransitionDirection {
    INWARDS,
    OUTWARDS
}

@OptIn(ExperimentalAnimationApi::class)
fun scaleIntoContainer(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.INWARDS,
    initialScale: Float = if (direction == ScaleTransitionDirection.OUTWARDS) 0.9f else 1.1f
): EnterTransition {
    return scaleIn(
        animationSpec = tween(ANIMATION_DURATION),
        initialScale = initialScale
    ) + fadeIn(animationSpec = tween(220))
}

@OptIn(ExperimentalAnimationApi::class)
fun scaleOutOfContainer(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.OUTWARDS,
    targetScale: Float = if (direction == ScaleTransitionDirection.INWARDS) 0.9f else 1.1f
): ExitTransition {
    return scaleOut(
        animationSpec = tween(
            durationMillis = ANIMATION_DURATION,
        ), targetScale = targetScale
    ) + fadeOut(tween(delayMillis = 90))
}

