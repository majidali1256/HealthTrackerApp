package com.healthtracker.app.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Health Log entity for storing general health entries like
 * nutrition, hydration, notes, and symptoms.
 */
@Entity(
    tableName = "health_logs",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId"), Index("timestamp"), Index("type")]
)
data class HealthLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val userId: String,
    
    // Log Type: NUTRITION, HYDRATION, SYMPTOM, NOTE, EXERCISE, MEDICATION
    val type: String,
    
    // Primary value (e.g., calories for nutrition, glasses for hydration)
    val value: Float,
    
    // Unit of measurement
    val unit: String? = null,
    
    // Additional details
    val title: String? = null,
    val description: String? = null,
    
    // For nutrition: barcode scan data
    val barcodeData: String? = null,
    
    // For exercise: duration in minutes
    val durationMinutes: Int? = null,
    
    // Timestamp
    val timestamp: Date = Date(),
    
    // Sync Status
    val isSynced: Boolean = false
)

/**
 * Enum for Health Log Types
 */
object HealthLogType {
    const val NUTRITION = "NUTRITION"
    const val HYDRATION = "HYDRATION"
    const val SYMPTOM = "SYMPTOM"
    const val NOTE = "NOTE"
    const val EXERCISE = "EXERCISE"
    const val MEDICATION = "MEDICATION"
}
