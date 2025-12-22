package com.healthtracker.app

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HealthTrackerApp : Application() {
    
    override fun onCreate() {
        try {
            Log.d("HealthTrackerApp", "Application onCreate starting...")
            super.onCreate()
            Log.d("HealthTrackerApp", "Application onCreate completed successfully")
        } catch (e: Exception) {
            Log.e("HealthTrackerApp", "Error in Application onCreate", e)
            throw e // Re-throw to see the actual error
        }
    }
}
