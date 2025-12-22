package com.healthtracker.app.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Sleep Record entity for tracking sleep patterns and quality.
 * Includes sleep stages (Deep, Light, REM) and sleep scores.
 */
@Entity(
    tableName = "sleep_records",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId"), Index("sleepStart")]
)
data class SleepRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val userId: String,
    
    // Sleep timing
    val sleepStart: Date,
    val sleepEnd: Date,
    
    // Total duration in minutes
    val totalMinutes: Int,
    
    // Sleep stages in minutes
    val deepSleepMinutes: Int = 0,
    val lightSleepMinutes: Int = 0,
    val remSleepMinutes: Int = 0,
    val awakeMinutes: Int = 0,
    
    // Sleep score (0-100)
    val sleepScore: Int? = null,
    
    // Number of times woken up
    val timesAwakened: Int = 0,
    
    // Source: MANUAL, SENSOR, WEARABLE
    val source: String = "MANUAL",
    
    // Notes
    val notes: String? = null,
    
    // Sync Status
    val isSynced: Boolean = false
)
