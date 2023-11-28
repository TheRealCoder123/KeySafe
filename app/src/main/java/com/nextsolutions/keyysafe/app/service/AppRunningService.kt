package com.nextsolutions.keyysafe.app.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.MainActivity

class AppRunningService : Service() {

    companion object {
        const val START_NOTIFICATION = "START_NOTIFICATION"
        const val CLOSE_NOTIFICATION = "CLOSE_NOTIFICATION"
        const val channelId = "key_safe_notification"

    }


    private val notificationId = 9918

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showAppRunningNotification()
        return START_STICKY

    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
        stopSelf()
    }

    private fun showAppRunningNotification() {

        val mainActivityIntent = Intent(this, MainActivity::class.java)
        mainActivityIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP // This flag ensures the activity isn't recreated.
        val pendingIntent = PendingIntent.getActivity(this, 0, mainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)


        val notification = NotificationCompat.Builder(this,channelId)
            .setContentTitle("KeySafe")
            .setContentText("KeySafe app is running")
            .setSmallIcon(R.drawable.baseline_app_settings_alt_24)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(notificationId, notification)
    }

}