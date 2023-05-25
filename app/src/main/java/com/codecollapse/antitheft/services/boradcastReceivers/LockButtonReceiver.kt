package com.codecollapse.antitheft.services.boradcastReceivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.codecollapse.antitheft.services.service.ChargerDetectorForegroundService
import com.codecollapse.antitheft.services.service.PhoneStabilityForegroundService
import com.codecollapse.antitheft.services.service.PocketDetectorForegroundService
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LockButtonReceiver @Inject constructor(
    var chargerDetectorForegroundService: ChargerDetectorForegroundService,
    var phoneStabilityForegroundService: PhoneStabilityForegroundService,
    var pocketDetectorForegroundService: PocketDetectorForegroundService,
    var notificationManager: NotificationManager
) :
    BroadcastReceiver() {
    companion object {
        private const val TAG = "LockButtonReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_SCREEN_OFF) {
            try {

                val serviceIntent = Intent(context, chargerDetectorForegroundService::class.java)
                context.stopService(serviceIntent)
                val phoneStabilityServiceIntent =
                    Intent(context, phoneStabilityForegroundService::class.java)
                context.stopService(phoneStabilityServiceIntent)
                val pocketDetectorIntent =
                    Intent(context, pocketDetectorForegroundService::class.java)
                context.stopService(pocketDetectorIntent)
                Timber.tag(TAG).d("LockButtonReceiver : Power Button Pressed")
            } catch (ex: Exception) {
                Timber.tag(TAG)
                    .d("LockButtonReceiver : Exception occurred with message ${ex.message}")
            }

        } else {
            Timber.tag(TAG).d("LockButtonReceiver")
        }
    }
}