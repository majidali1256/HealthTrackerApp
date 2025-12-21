package com.healthtracker.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.healthtracker.app.data.local.dao.*
import com.healthtracker.app.data.local.entities.*

/**
 * Main Room Database for Health Tracker app.
 * Contains all health-related entities with offline-first support.
 */
@Database(
    entities = [
        User::class,
        HealthLog::class,
        VitalRecord::class,
        SleepRecord::class,
        ActivityRecord::class,
        Medication::class,
        Document::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun healthLogDao(): HealthLogDao
    abstract fun vitalRecordDao(): VitalRecordDao
    abstract fun sleepRecordDao(): SleepRecordDao
    abstract fun activityRecordDao(): ActivityRecordDao
    abstract fun medicationDao(): MedicationDao
    abstract fun documentDao(): DocumentDao
    
    companion object {
        const val DATABASE_NAME = "health_tracker_db"
    }
}
