package com.healthtracker.app.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Data
import java.util.concurrent.TimeUnit

/**
 * WorkManager worker for medication reminders.
 * Checks for medications scheduled at specific times and triggers notifications.
 */
class MedicationReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result {
        val notificationHelper = NotificationHelper(applicationContext)
        
        val medicationName = inputData.getString(KEY_MEDICATION_NAME) ?: "Your medication"
        val dosage = inputData.getString(KEY_DOSAGE) ?: ""
        
        notificationHelper.showMedicationReminder(medicationName, dosage)
        
        return Result.success()
    }
    
    companion object {
        private const val WORK_NAME_PREFIX = "medication_reminder_"
        const val KEY_MEDICATION_NAME = "medication_name"
        const val KEY_DOSAGE = "dosage"
        const val KEY_MEDICATION_ID = "medication_id"
        
        /**
         * Schedule a daily reminder for a medication
         * @param context Application context
         * @param medicationId Unique ID for this medication
         * @param medicationName Name of the medication
         * @param dosage Dosage information
         * @param hourOfDay Hour to remind (0-23)
         * @param minute Minute to remind (0-59)
         */
        fun scheduleDailyReminder(
            context: Context,
            medicationId: Long,
            medicationName: String,
            dosage: String,
            hourOfDay: Int,
            minute: Int
        ) {
            // Calculate initial delay until the scheduled time
            val now = java.util.Calendar.getInstance()
            val target = java.util.Calendar.getInstance().apply {
                set(java.util.Calendar.HOUR_OF_DAY, hourOfDay)
                set(java.util.Calendar.MINUTE, minute)
                set(java.util.Calendar.SECOND, 0)
                set(java.util.Calendar.MILLISECOND, 0)
            }
            
            // If time has passed today, schedule for tomorrow
            if (target.before(now)) {
                target.add(java.util.Calendar.DAY_OF_MONTH, 1)
            }
            
            val initialDelayMs = target.timeInMillis - now.timeInMillis
            val initialDelayMinutes = (initialDelayMs / 1000 / 60).coerceAtLeast(1)
            
            val inputData = Data.Builder()
                .putString(KEY_MEDICATION_NAME, medicationName)
                .putString(KEY_DOSAGE, dosage)
                .putLong(KEY_MEDICATION_ID, medicationId)
                .build()
            
            val request = PeriodicWorkRequestBuilder<MedicationReminderWorker>(
                24, TimeUnit.HOURS,
                15, TimeUnit.MINUTES // flex interval
            )
                .setInitialDelay(initialDelayMinutes, TimeUnit.MINUTES)
                .setInputData(inputData)
                .build()
            
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "$WORK_NAME_PREFIX$medicationId",
                ExistingPeriodicWorkPolicy.REPLACE,
                request
            )
        }
        
        /**
         * Schedule medication reminders at common times
         * Morning (8 AM), Afternoon (2 PM), Evening (8 PM)
         */
        fun scheduleCommonReminders(
            context: Context,
            medicationId: Long,
            medicationName: String,
            dosage: String,
            times: List<String> // e.g., ["MORNING", "AFTERNOON", "EVENING"]
        ) {
            times.forEach { time ->
                val (hour, minute) = when (time.uppercase()) {
                    "MORNING" -> 8 to 0
                    "AFTERNOON" -> 14 to 0
                    "EVENING" -> 20 to 0
                    "NIGHT" -> 22 to 0
                    else -> return@forEach
                }
                
                scheduleDailyReminder(
                    context,
                    medicationId + time.hashCode(), // Unique ID for each time
                    medicationName,
                    dosage,
                    hour,
                    minute
                )
            }
        }
        
        /**
         * Cancel a specific medication reminder
         */
        fun cancelReminder(context: Context, medicationId: Long) {
            WorkManager.getInstance(context).cancelUniqueWork("$WORK_NAME_PREFIX$medicationId")
        }
        
        /**
         * Cancel all medication reminders
         */
        fun cancelAllReminders(context: Context) {
            WorkManager.getInstance(context).cancelAllWorkByTag(WORK_NAME_PREFIX)
        }
    }
}
