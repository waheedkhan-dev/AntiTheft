package com.codecollapse.antitheft.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import timber.log.Timber
import kotlin.math.sqrt

class PocketDetector(private val context: Context) : SensorEventListener {
    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val proximitySensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    private var listener: PocketListener? = null

    interface PocketListener {
        fun onPhoneInPocket()
        fun onPhoneOutOfPocket()
    }

    fun setPocketListener(listener: PocketListener) {
        this.listener = listener
    }

    fun startListening() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            val distance = event.values[0]
            if (distance >= (proximitySensor?.maximumRange ?: 0).toDouble()) {
                listener?.onPhoneOutOfPocket()
            } else {
                listener?.onPhoneInPocket()
            }
        } else if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val acceleration = sqrt((x * x + y * y + z * z).toDouble())

            if (acceleration > THRESHOLD) {
                Timber.d("onPhoneOutOfPocket value = $acceleration")
                listener?.onPhoneOutOfPocket()
            } else {
                Timber.d("onPhoneInPocket value = $acceleration")
                listener?.onPhoneInPocket()
            }
        }
    }

    companion object {
        private const val THRESHOLD = 9.8 // Adjust this threshold as needed
    }
}