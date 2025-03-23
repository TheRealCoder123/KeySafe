package com.nextsolutions.keyysafe.settings.ui.Database.workers

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.MainActivity
import com.nextsolutions.keyysafe.app.service.AppRunningService
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.BACKUP_DATA_PASSWORD
import com.nextsolutions.keyysafe.settings.ui.Database.domain.repository.BackUpRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject


@HiltWorker
class AutoBackupWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(
    context,
    workerParameters
){


    @Inject
    lateinit var backUpRepository: BackUpRepository

    @Inject
    lateinit var notificationManager: NotificationManager

    override suspend fun doWork(): Result {
        val backUpResponse = backUpRepository.export("Keysafe_Auto_Backup", BACKUP_DATA_PASSWORD)

        if (backUpResponse.success){
            notify("Successfully backed up your data", "Keysafe Auto Backup")
        }else{
            notify("Something went wrong backing up your data, with error: ${backUpResponse.error}", "Keysafe Auto Backup")
        }

        Log.e("backup response from auto backup", backUpResponse.toString())
        return Result.success()
    }

    private fun notify(text: String, title: String){
        val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
        mainActivityIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, mainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)


        val notification = NotificationCompat.Builder(applicationContext, AppRunningService.channelId)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.keysafe_logo)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(21, notification)
    }

}