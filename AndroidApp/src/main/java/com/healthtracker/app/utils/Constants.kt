package com.healthtracker.app.utils

/**
 * App-wide constants
 */
object Constants {
    
    // Step Counter
    const val STEPS_GOAL_DEFAULT = 10000
    const val ACTIVE_MINUTES_GOAL_DEFAULT = 30
    const val CALORIES_GOAL_DEFAULT = 500
    
    // Hydration
    const val HYDRATION_GOAL_GLASSES = 8
    const val GLASS_ML = 250 // ml per glass
    
    // Sleep
    const val RECOMMENDED_SLEEP_HOURS = 8f
    const val MIN_SLEEP_HOURS = 6f
    
    // Vitals (Normal ranges for adults)
    const val HEART_RATE_MIN_NORMAL = 60
    const val HEART_RATE_MAX_NORMAL = 100
    const val SPO2_MIN_NORMAL = 95
    const val BP_SYSTOLIC_NORMAL = 120
    const val BP_DIASTOLIC_NORMAL = 80
    
    // Notifications
    const val MEDICATION_REMINDER_CHANNEL_ID = "medication_reminders"
    const val HEALTH_ALERTS_CHANNEL_ID = "health_alerts"
    const val STEP_COUNTER_CHANNEL_ID = "step_counter"
    
    // Request Codes
    const val RC_SIGN_IN = 1001
    const val RC_CAMERA_PERMISSION = 1002
    const val RC_LOCATION_PERMISSION = 1003
    const val RC_ACTIVITY_RECOGNITION = 1004
    
    // SharedPrefs Keys
    const val PREF_USER_ID = "user_id"
    const val PREF_ONBOARDING_COMPLETE = "onboarding_complete"
    const val PREF_LAST_SYNC = "last_sync_timestamp"
}
