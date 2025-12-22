package com.healthtracker.app.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.R
import com.healthtracker.app.data.local.dao.ActivityRecordDao
import com.healthtracker.app.data.local.entities.ActivityRecord
import com.healthtracker.app.ui.dashboard.DashboardActivity
import com.healthtracker.app.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

/**
 * Foreground service for continuous step counting.
 * Uses hardware step counter sensor for accurate, battery-efficient tracking.
 */
@AndroidEntryPoint
class StepCounterService : Service(), SensorEventListener {

    @Inject
    lateinit var activityRecordDao: ActivityRecordDao
    
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null
    private var stepDetectorSensor: Sensor? = null
    
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    private var initialStepCount: Int = -1
    private var currentTotalSteps: Int = 0
    private var todayActivityId: Long = -1

    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = Constants.STEP_COUNTER_CHANNEL_ID
        
        fun startService(context: Context) {
            val intent = Intent(context, StepCounterService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
        
        fun stopService(context: Context) {
            val intent = Intent(context, StepCounterService::class.java)
            context.stopService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        
        // Prefer step counter (more accurate, less battery)
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        
        createNotificationChannel()
        initializeTodayRecord()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, createNotification(0))
        registerSensors()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }

    private fun registerSensors() {
        stepCounterSensor?.let { sensor ->
            sensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_UI
            )
        }
        
        // Fallback to step detector if counter not available
        if (stepCounterSensor == null) {
            stepDetectorSensor?.let { sensor ->
                sensorManager.registerListener(
                    this,
                    sensor,
                    SensorManager.SENSOR_DELAY_FASTEST
                )
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return
        
        when (event.sensor.type) {
            Sensor.TYPE_STEP_COUNTER -> {
                val totalSteps = event.values[0].toInt()
                
                if (initialStepCount < 0) {
                    // First reading since boot
                    initialStepCount = totalSteps
                    loadSavedSteps()
                } else {
                    // Calculate steps since service started
                    val stepsSinceStart = totalSteps - initialStepCount
                    val newSteps = stepsSinceStart + getSavedBaseSteps()
                    
                    if (newSteps != currentTotalSteps) {
                        currentTotalSteps = newSteps
                        updateStepCount(currentTotalSteps)
                    }
                }
            }
            Sensor.TYPE_STEP_DETECTOR -> {
                // Each event = 1 step detected
                currentTotalSteps++
                updateStepCount(currentTotalSteps)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for step counting
    }

    private fun initializeTodayRecord() {
        serviceScope.launch {
            val userId = firebaseAuth.currentUser?.uid ?: return@launch
            val today = getStartOfDay()
            
            var record = activityRecordDao.getByDate(userId, today)
            if (record == null) {
                record = ActivityRecord(
                    userId = userId,
                    date = today,
                    steps = 0,
                    stepsGoal = Constants.STEPS_GOAL_DEFAULT,
                    caloriesGoal = Constants.CALORIES_GOAL_DEFAULT,
                    activeMinutesGoal = Constants.ACTIVE_MINUTES_GOAL_DEFAULT
                )
                todayActivityId = activityRecordDao.insert(record)
            } else {
                todayActivityId = record.id
                currentTotalSteps = record.steps
            }
        }
    }

    private fun loadSavedSteps() {
        serviceScope.launch {
            val userId = firebaseAuth.currentUser?.uid ?: return@launch
            val record = activityRecordDao.getByDate(userId, getStartOfDay())
            record?.let {
                currentTotalSteps = it.steps
            }
        }
    }

    private fun getSavedBaseSteps(): Int {
        // This would typically load from SharedPreferences
        return 0
    }

    private fun updateStepCount(steps: Int) {
        serviceScope.launch {
            if (todayActivityId > 0) {
                // Calculate calories burned (rough estimate: 0.04 kcal per step)
                val calories = (steps * 0.04).toInt()
                
                activityRecordDao.updateSteps(todayActivityId, steps, Date())
                
                // Update notification
                val notification = createNotification(steps)
                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.notify(NOTIFICATION_ID, notification)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Step Counter",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows your daily step count"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(steps: Int): Notification {
        val intent = Intent(this, DashboardActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Steps Today")
            .setContentText("$steps / ${Constants.STEPS_GOAL_DEFAULT} steps")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    private fun getStartOfDay(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
}
