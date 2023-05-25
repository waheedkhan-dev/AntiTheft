package com.codecollapse.antitheft.di

import android.app.NotificationManager
import android.content.Context
import com.codecollapse.antitheft.ServiceRepository
import com.codecollapse.antitheft.services.service.PhoneStabilityForegroundService
import com.codecollapse.antitheft.services.service.PocketDetectorForegroundService
import com.codecollapse.antitheft.utils.NotificationUtil
import com.codecollapse.antitheft.utils.PocketDetector
import com.codecollapse.antitheft.utils.StabilityDetector
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideNotification(): NotificationUtil {
        return NotificationUtil()
    }

    @Singleton
    @Provides
    fun providePocketDetector(@ApplicationContext context: Context): PocketDetector {
        return PocketDetector(context)
    }

    @Singleton
    @Provides
    fun providePhoneStabilityDetector(@ApplicationContext context: Context): StabilityDetector {
        return StabilityDetector(context)
    }

    @Singleton
    @Provides
    fun provideServiceRepository(
        phoneStabilityForegroundService: PhoneStabilityForegroundService,
        pocketDetectorForegroundService: PocketDetectorForegroundService
    ): ServiceRepository {
        return ServiceRepository(
            phoneStabilityForegroundService = phoneStabilityForegroundService,
            pocketDetectorForegroundService
        )
    }

    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}