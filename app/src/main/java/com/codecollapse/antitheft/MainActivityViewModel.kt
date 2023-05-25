package com.codecollapse.antitheft

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.codecollapse.antitheft.services.boradcastReceivers.ChargerStatusReceiver
import com.codecollapse.antitheft.services.boradcastReceivers.LockButtonReceiver
import com.codecollapse.antitheft.utils.PocketDetector
import com.codecollapse.antitheft.utils.StabilityDetector
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private var lockButtonReceiver: LockButtonReceiver,
    private var chargerStatusReceiver: ChargerStatusReceiver,
    private var pocketDetector: PocketDetector,
    private var stabilityDetector: StabilityDetector,
    private var serviceRepository: ServiceRepository,
    @ApplicationContext context: Context
) :
    ViewModel(), StabilityDetector.StabilityListener,PocketDetector.PocketListener {
    companion object {
        private const val TAG = "MainActivityViewModel"
    }

    private var mContext = context
    private var isPhoneStable = false
    private var isPhoneStabilityServiceIsRunning = false
    private var isPhoneOutOfPocket = false
    private var isPocketDetectorServiceIsRunning = false
    val pocketRemovalTextState = mutableStateOf("Pocket Removal Service is not started yet")
    val chargerRemovalTextState = mutableStateOf("Charger Removal Service is not started yet")
    val motionDetectorTextState = mutableStateOf("Motion Detector Service is not started yet")

    val pocketRemovalButtonState = mutableStateOf("Start Pocket Removal Service")
    val chargerRemovalButtonState = mutableStateOf("Start Charger Removal Service")
    val motionDetectorButtonState = mutableStateOf("Start Motion Detector Service")

    fun startChargerService(context: Context) {
        val chargerStatusReceiverFilter = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        context.registerReceiver(chargerStatusReceiver, chargerStatusReceiverFilter)
    }

    fun startLockButtonService(context: Context) {
        val lockButtonReceiverFilter = IntentFilter(Intent.ACTION_SCREEN_OFF)
        context.registerReceiver(lockButtonReceiver, lockButtonReceiverFilter)
    }

    fun startMotionDetectorService() {
        stabilityDetector.setStabilityListener(this)
        stabilityDetector.startListening()
    }

    fun startPocketDetectorService() {
        pocketDetector.setPocketListener(this)
        pocketDetector.startListening()
    }

    fun unRegisterChargerService(context: Context) {
        context.unregisterReceiver(chargerStatusReceiver)
    }

    fun unRegisterLockService(context: Context) {
        context.unregisterReceiver(lockButtonReceiver)
    }

    fun changePocketRemovalState(message: String) {
        pocketRemovalTextState.value = message
    }

    fun changeChargerRemovalState(message: String) {
        chargerRemovalTextState.value = message
    }

    fun changeMotionDetectorState(message: String) {
        motionDetectorTextState.value = message
    }

    override fun onPhoneStable() {
        Timber.tag(TAG).d("Phone is stable")
        if(isPhoneStable.not()){
            isPhoneStable = true
        }
        /*if (isPhoneStable.not()) {
            isPhoneStable = true
            isPhoneStabilityServiceIsRunning = false
            val serviceIntent =
                Intent(mContext, serviceRepository.getStabilityServiceObject()::class.java)
            mContext.stopService(serviceIntent)
            Timber.tag(TAG).d("Phone is stable")
        }*/
    }

    override fun onPhoneUnstable() {
        if (isPhoneStable && isPhoneStabilityServiceIsRunning.not()) {
            isPhoneStable = false
            isPhoneStabilityServiceIsRunning = true
            val serviceIntent =
                Intent(mContext, serviceRepository.getStabilityServiceObject()::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // For Android Oreo and above, start as foreground service
                mContext.startForegroundService(serviceIntent)
            } else {
                // For older Android versions, start as regular service
                mContext.startService(serviceIntent)
            }

            Timber.tag(TAG).d("Phone is Unstable")
        }
    }

    override fun onPhoneInPocket() {
        Timber.tag(TAG).d("onPhoneInPocket")
    }

    override fun onPhoneOutOfPocket() {
        if (isPhoneOutOfPocket.not() && isPocketDetectorServiceIsRunning.not()) {
            isPhoneOutOfPocket = true
            isPocketDetectorServiceIsRunning = true
            val serviceIntent =
                Intent(mContext, serviceRepository.getPocketDetectorServiceObject()::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // For Android Oreo and above, start as foreground service
                mContext.startForegroundService(serviceIntent)
            } else {
                // For older Android versions, start as regular service
                mContext.startService(serviceIntent)
            }
        }
        Timber.tag(TAG).d("onPhoneOutOfPocket")
    }
}