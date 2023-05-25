package com.codecollapse.antitheft

import com.codecollapse.antitheft.services.service.PhoneStabilityForegroundService
import com.codecollapse.antitheft.services.service.PocketDetectorForegroundService
import javax.inject.Inject

class ServiceRepository @Inject constructor(
    private var phoneStabilityForegroundService: PhoneStabilityForegroundService,
    private var pocketDetectorForegroundService: PocketDetectorForegroundService
) {

    fun getStabilityServiceObject(): PhoneStabilityForegroundService {
        return phoneStabilityForegroundService
    }

    fun getPocketDetectorServiceObject(): PocketDetectorForegroundService {
        return pocketDetectorForegroundService
    }
}