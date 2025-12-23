package com.healthtracker.app.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

/**
 * WorkManager worker for scheduled hydration reminders.
 * Runs every 2 hours during waking hours to remind users to drink water.
 */
class HydrationReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result {
        val notificationHelper = NotificationHelper(applicationContext)
        
        // Only show notification during waking hours (8 AM - 10 PM)
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        if (hour in 8..22) {
            notificationHelper.showHydrationReminder()
        }
        
        return Result.success()
    }
    
    companion object {
        private const val WORK_NAME = "hydration_reminder"
        
        /**
         * Schedule periodic hydration reminders every 2 hours
         */
        fun schedule(context: Context) {
            val request = PeriodicWorkRequestBuilder<HydrationReminderWorker>(
                2, TimeUnit.HOURS,
                15, TimeUnit.MINUTES // flex interval
            ).build()
            
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
        
        /**
         * Cancel hydration reminders
         */
        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
        }
    }
}
