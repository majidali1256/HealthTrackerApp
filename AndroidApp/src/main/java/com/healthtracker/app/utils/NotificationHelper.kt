package com.healthtracker.app.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.healthtracker.app.R
import com.healthtracker.app.ui.dashboard.DashboardActivity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Helper class for creating and showing notifications.
 * Handles notification channels and reminder notifications.
 */
@Singleton
class NotificationHelper @Inject constructor(
    private val context: Context
) {
    companion object {
        const val CHANNEL_MEDICATION = "medication_reminders"
        const val CHANNEL_HYDRATION = "hydration_reminders"
        const val CHANNEL_HEALTH = "health_alerts"
        
        const val NOTIFICATION_MEDICATION = 1001
        const val NOTIFICATION_HYDRATION = 1002
        const val NOTIFICATION_HEALTH = 1003
    }
    
    init {
        createNotificationChannels()
    }
    
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            
            // Medication channel
            val medicationChannel = NotificationChannel(
                CHANNEL_MEDICATION,
                "Medication Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Reminders to take your medications"
                enableVibration(true)
            }
            
            // Hydration channel
            val hydrationChannel = NotificationChannel(
                CHANNEL_HYDRATION,
                "Hydration Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Reminders to drink water"
            }
            
            // Health alerts channel
            val healthChannel = NotificationChannel(
                CHANNEL_HEALTH,
                "Health Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Important health alerts and warnings"
                enableVibration(true)
            }
            
            notificationManager.createNotificationChannels(
                listOf(medicationChannel, hydrationChannel, healthChannel)
            )
        }
    }
    
    /**
     * Show medication reminder notification
     */
    fun showMedicationReminder(medicationName: String, dosage: String) {
        val intent = Intent(context, DashboardActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_MEDICATION)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("💊 Time to take your medication")
            .setContentText("$medicationName - $dosage")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_MEDICATION, notification)
    }
    
    /**
     * Show hydration reminder notification
     */
    fun showHydrationReminder() {
        val intent = Intent(context, DashboardActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_HYDRATION)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("💧 Stay hydrated!")
            .setContentText("Don't forget to drink a glass of water")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_HYDRATION, notification)
    }
    
    /**
     * Show health alert notification
     */
    fun showHealthAlert(title: String, message: String) {
        val intent = Intent(context, DashboardActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_HEALTH)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_HEALTH, notification)
    }
}
