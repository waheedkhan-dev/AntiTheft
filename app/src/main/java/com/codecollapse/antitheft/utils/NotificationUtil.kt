package com.codecollapse.antitheft.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.codecollapse.antitheft.R
import javax.inject.Inject


class NotificationUtil @Inject constructor() {

    fun sendNotification(
        messageTitle: String,
        messageBody: String,
        notificationSound : Uri,
        notificationId : Int,
        context: Context
    ): Notification {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context, 0 /* Request code */, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "notification_channel"
       //RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder =
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

         val notificationManager = context
             .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Custom Notification Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)

            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            // Set custom sound for the channel

            channel.setSound(notificationSound, audioAttributes)
         /*   val channel = NotificationChannel(
                channelId,
                "ANTI-THEFT-Notification-Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.setSound(defaultSoundUri,audioAttributes)*/
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId , notificationBuilder.build())
        return notificationBuilder.build()
    }
}


