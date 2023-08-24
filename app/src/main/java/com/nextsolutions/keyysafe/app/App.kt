package com.nextsolutions.keyysafe.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreKeys
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreManager
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesKeys
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesManager
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@Composable
fun App(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    KeySafeTheme {
        val navHostController = rememberNavController()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(KeySafeTheme.colors.background),
        ) {
            MainNavigationGraph(
                navHostController = navHostController,
                isAuthSetup =  mainViewModel.isAuthSetup
            )
        }

    }
}