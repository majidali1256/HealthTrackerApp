package com.healthtracker.app.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Activity Record entity for tracking physical activities
 * like steps, distance, calories burned, and active minutes.
 */
@Entity(
    tableName = "activity_records",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId"), Index("date")]
)
data class ActivityRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val userId: String,
    
    // Date for this activity record (one per day typically)
    val date: Date,
    
    // Steps
    val steps: Int = 0,
    val stepsGoal: Int = 10000,
    
    // Distance in meters
    val distanceMeters: Float = 0f,
    
    // Calories burned
    val caloriesBurned: Int = 0,
    val caloriesGoal: Int = 500,
    
    // Active minutes
    val activeMinutes: Int = 0,
    val activeMinutesGoal: Int = 30,
    
    // Floors climbed
    val floorsClimbed: Int = 0,
    
    // Source: MANUAL, SENSOR, WEARABLE
    val source: String = "SENSOR",
    
    // Timestamps
    val lastUpdated: Date = Date(),
    
    // Sync Status
    val isSynced: Boolean = false
)
