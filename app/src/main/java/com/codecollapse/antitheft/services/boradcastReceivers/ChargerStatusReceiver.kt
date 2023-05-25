package com.codecollapse.antitheft.services.boradcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.codecollapse.antitheft.services.service.ChargerDetectorForegroundService
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ChargerStatusReceiver @Inject constructor(var chargerDetectorForegroundService: ChargerDetectorForegroundService) :
    BroadcastReceiver() {
    companion object {
        private const val TAG = "ChargerStatusReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                Timber.tag(TAG).d("Phone is connected to a charger")
            }

            Intent.ACTION_POWER_DISCONNECTED -> {
                Timber.tag(TAG).d("Phone is disconnected from the charger")
                startForegroundService(
                    context = context,
                    chargerDetectorForegroundService = chargerDetectorForegroundService
                )
            }
        }
    }
}

private fun startForegroundService(
    context: Context,
    chargerDetectorForegroundService: ChargerDetectorForegroundService
) {
    val serviceIntent = Intent(context, chargerDetectorForegroundService::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // For Android Oreo and above, start as foreground service
        context.startForegroundService(serviceIntent)
    } else {
        // For older Android versions, start as regular service
        context.startService(serviceIntent)
    }
}

