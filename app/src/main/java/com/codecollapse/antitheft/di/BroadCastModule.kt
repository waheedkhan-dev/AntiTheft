package com.codecollapse.antitheft.di

import android.app.NotificationManager
import com.codecollapse.antitheft.services.boradcastReceivers.ChargerStatusReceiver
import com.codecollapse.antitheft.services.boradcastReceivers.LockButtonReceiver
import com.codecollapse.antitheft.services.service.ChargerDetectorForegroundService
import com.codecollapse.antitheft.services.service.PhoneStabilityForegroundService
import com.codecollapse.antitheft.services.service.PocketDetectorForegroundService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BroadCastModule {

    @Provides
    fun provideLockButtonReceiver(
        chargerDetectorForegroundService: ChargerDetectorForegroundService,
        phoneStabilityForegroundService: PhoneStabilityForegroundService,
        pocketDetectorForegroundService: PocketDetectorForegroundService,
        notificationManager: NotificationManager
    ): LockButtonReceiver {
        return LockButtonReceiver(
            chargerDetectorForegroundService,
            phoneStabilityForegroundService,
            pocketDetectorForegroundService,
            notificationManager
        )
    }

    @Provides
    fun provideChargerStatusReceiver(chargerDetectorForegroundService: ChargerDetectorForegroundService): ChargerStatusReceiver {
        return ChargerStatusReceiver(chargerDetectorForegroundService)
    }
}