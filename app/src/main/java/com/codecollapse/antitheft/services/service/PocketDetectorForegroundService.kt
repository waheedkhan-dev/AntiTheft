package com.codecollapse.antitheft.services.service

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import com.codecollapse.antitheft.R
import com.codecollapse.antitheft.utils.NotificationUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PocketDetectorForegroundService :
    Service() {

    @Inject
    lateinit var notificationUtil: NotificationUtil
    override fun onCreate() {
        super.onCreate()
        // Perform any setup or initialization here
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start your foreground service tasks here
        val defaultSoundUri =
            Uri.parse("android.resource://${packageName}/${R.raw.charger_removal}")
        val notification = notificationUtil.sendNotification(
            messageTitle = "Pocket Detector",
            messageBody = "Phone is out of pocket",
            notificationSound = defaultSoundUri,
            notificationId = 102,
            context = this
        )
        startForeground(102, notification)
        // Return START_STICKY to ensure the service restarts if killed by the system
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // stopForeground(STOP_FOREGROUND_REMOVE)
        // Clean up any resources or tasks here
    }

    override fun onBind(intent: Intent?): IBinder? {
        // Return null because this is not a bound service
        return null
    }
}