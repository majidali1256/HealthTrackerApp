package com.healthtracker.app

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HealthTrackerApp : Application() {
    
    override fun onCreate() {
        try {
            Log.d("HealthTrackerApp", "Application onCreate starting...")
            super.onCreate()
            
            // Apply saved theme preference on app startup
            applyThemeFromPreferences()
            
            Log.d("HealthTrackerApp", "Application onCreate completed successfully")
        } catch (e: Exception) {
            Log.e("HealthTrackerApp", "Error in Application onCreate", e)
            throw e // Re-throw to see the actual error
        }
    }
    
    private fun applyThemeFromPreferences() {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean("dark_mode", true) // Default to dark mode
        
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES 
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        
        Log.d("HealthTrackerApp", "Theme applied: ${if (isDarkMode) "Dark" else "Light"}")
    }
}
