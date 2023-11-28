package com.nextsolutions.keyysafe.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.hilt.work.HiltWorkerFactory
import com.nextsolutions.keyysafe.app.service.AppRunningService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class KeySafeApp : Application(), androidx.work.Configuration.Provider {


    @Inject
    lateinit var workerFactory: HiltWorkerFactory



    override fun getWorkManagerConfiguration(): androidx.work.Configuration {
        return androidx.work.Configuration.Builder().setWorkerFactory(workerFactory).build()
    }



    override fun onCreate() {
        super.onCreate()
        val notificationChannel = NotificationChannel(
            AppRunningService.channelId,
            "KeySafe app running",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }


}