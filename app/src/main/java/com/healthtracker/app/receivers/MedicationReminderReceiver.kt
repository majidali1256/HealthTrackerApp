package com.healthtracker.app.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.healthtracker.app.ui.dashboard.DashboardActivity
import com.healthtracker.app.utils.Constants

/**
 * Receiver for medication reminder alarms.
 * Shows notification when it's time to take medication.
 */
class MedicationReminderReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_MEDICATION_ID = "medication_id"
        const val EXTRA_MEDICATION_NAME = "medication_name"
        const val EXTRA_DOSAGE = "dosage"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val medicationId = intent.getLongExtra(EXTRA_MEDICATION_ID, -1)
        val medicationName = intent.getStringExtra(EXTRA_MEDICATION_NAME) ?: "Medication"
        val dosage = intent.getStringExtra(EXTRA_DOSAGE) ?: ""

        createNotificationChannel(context)
        showNotification(context, medicationId.toInt(), medicationName, dosage)
    }

    private fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            Constants.MEDICATION_REMINDER_CHANNEL_ID,
            "Medication Reminders",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Reminders to take your medications"
            enableVibration(true)
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification(
        context: Context,
        notificationId: Int,
        medicationName: String,
        dosage: String
    ) {
        val intent = Intent(context, DashboardActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, Constants.MEDICATION_REMINDER_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_agenda)
            .setContentTitle("Time for $medicationName")
            .setContentText("Take $dosage now")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(
                android.R.drawable.ic_menu_save,
                "Taken",
                createTakenPendingIntent(context, notificationId)
            )
            .addAction(
                android.R.drawable.ic_popup_sync,
                "Snooze",
                createSnoozePendingIntent(context, notificationId)
            )
            .build()

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.notify(notificationId, notification)
    }

    private fun createTakenPendingIntent(context: Context, notificationId: Int): PendingIntent {
        val intent = Intent(context, MedicationActionReceiver::class.java).apply {
            action = MedicationActionReceiver.ACTION_TAKEN
            putExtra(EXTRA_MEDICATION_ID, notificationId.toLong())
        }
        return PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createSnoozePendingIntent(context: Context, notificationId: Int): PendingIntent {
        val intent = Intent(context, MedicationActionReceiver::class.java).apply {
            action = MedicationActionReceiver.ACTION_SNOOZE
            putExtra(EXTRA_MEDICATION_ID, notificationId.toLong())
        }
        return PendingIntent.getBroadcast(
            context,
            notificationId + 1000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}

/**
 * Receiver for medication notification actions (Taken/Snooze).
 */
class MedicationActionReceiver : BroadcastReceiver() {
    
    companion object {
        const val ACTION_TAKEN = "com.healthtracker.app.MEDICATION_TAKEN"
        const val ACTION_SNOOZE = "com.healthtracker.app.MEDICATION_SNOOZE"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val medicationId = intent.getLongExtra(MedicationReminderReceiver.EXTRA_MEDICATION_ID, -1)
        
        when (intent.action) {
            ACTION_TAKEN -> {
                // TODO: Log medication taken in database
                dismissNotification(context, medicationId.toInt())
            }
            ACTION_SNOOZE -> {
                // TODO: Reschedule alarm for 15 minutes later
                dismissNotification(context, medicationId.toInt())
            }
        }
    }

    private fun dismissNotification(context: Context, notificationId: Int) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.cancel(notificationId)
    }
}
