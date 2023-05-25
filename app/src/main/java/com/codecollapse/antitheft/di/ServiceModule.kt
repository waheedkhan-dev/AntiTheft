package com.codecollapse.antitheft.di

import com.codecollapse.antitheft.services.service.ChargerDetectorForegroundService
import com.codecollapse.antitheft.services.service.PhoneStabilityForegroundService
import com.codecollapse.antitheft.services.service.PocketDetectorForegroundService
import com.codecollapse.antitheft.utils.NotificationUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideChargerDetectorService() : ChargerDetectorForegroundService{
        return ChargerDetectorForegroundService()
    }

    @Singleton
    @Provides
    fun providePhoneStabilityService() : PhoneStabilityForegroundService{
        return PhoneStabilityForegroundService()
    }

    @Singleton
    @Provides
    fun providePocketDetectorService() : PocketDetectorForegroundService{
        return PocketDetectorForegroundService()
    }
}