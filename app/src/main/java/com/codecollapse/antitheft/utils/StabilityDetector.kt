package com.codecollapse.antitheft.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class StabilityDetector(context: Context) : SensorEventListener {
    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var listener: StabilityListener? = null

    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var lastZ: Float = 0f

    private var isStable: Boolean = true

    interface StabilityListener {
        fun onPhoneStable()
        fun onPhoneUnstable()
    }

    fun setStabilityListener(listener: StabilityListener) {
        this.listener = listener
    }

    fun startListening() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val currentX: Float = event?.values?.get(0) ?: 0f
        val currentY: Float = event?.values?.get(1) ?: 0f
        val currentZ: Float = event?.values?.get(2) ?: 0f

        val deltaX: Float = lastX - currentX
        val deltaY: Float = lastY - currentY
        val deltaZ: Float = lastZ - currentZ

        lastX = currentX
        lastY = currentY
        lastZ = currentZ

        val acceleration: Float = sqrt((deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ).toDouble()).toFloat()

        isStable = acceleration <= THRESHOLD

        if (isStable) {
            listener?.onPhoneStable()
        } else {
            listener?.onPhoneUnstable()
        }
    }

    companion object {
        private const val THRESHOLD = 0.8f
    }
}