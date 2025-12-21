package com.healthtracker.app.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.healthtracker.app.services.StepCounterService

/**
 * Receiver to restart step counter service after device boot.
 */
class BootReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            StepCounterService.startService(context)
        }
    }
}
