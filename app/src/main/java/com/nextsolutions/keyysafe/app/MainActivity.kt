package com.nextsolutions.keyysafe.app

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.nextsolutions.keyysafe.app.service.AppRunningService
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesManager
import com.nextsolutions.keyysafe.settings.ui.Appearance.Language.LanguageConfig
import com.nextsolutions.keyysafe.settings.ui.Appearance.Language.LanguageConfig.changeLanguage
import com.nextsolutions.keyysafe.settings.ui.Database.workers.AutoBackupWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private lateinit var mainViewModel: MainViewModel


    override fun attachBaseContext(newBase: Context?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU){
            val sharedPrefs = PreferencesManager(newBase!!)
            val languageCode: String = sharedPrefs.getString(LanguageConfig.SELECTED_LANGUAGE_KEY, "en").toString()
            Log.e("lang", "$languageCode")
            val context: Context = changeLanguage(newBase, languageCode)
            super.attachBaseContext(context)
        }else{
            super.attachBaseContext(newBase)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val autoBackUp = PeriodicWorkRequest.Builder(
            AutoBackupWorker::class.java,
            6,
            TimeUnit.HOURS
        ).build()

        setContent {
            mainViewModel = hiltViewModel()

            if (mainViewModel.isAutoBackupOn){
                WorkManager.getInstance(this).enqueue(autoBackUp)
            }else{
                WorkManager.getInstance(this).cancelWorkById(autoBackUp.id)
            }

            if (mainViewModel.openNotificationWhileRunning){
                startService(Intent(this, AppRunningService::class.java))
            }else{
                stopService(Intent(this, AppRunningService::class.java))
            }

            setSecureFlag(mainViewModel.isAllowedToTakeScreenshots)

            App(mainViewModel)
        }
    }

    private fun setSecureFlag(enable: Boolean) {
        if (enable) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }

    override fun onRestart() {
        super.onRestart()
        mainViewModel.shouldOpenAuthScreen = true
    }




}

