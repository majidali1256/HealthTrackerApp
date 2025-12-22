package com.healthtracker.app.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Vital Record entity for storing biometric measurements
 * like heart rate, SpO2, blood pressure, and respiratory rate.
 */
@Entity(
    tableName = "vital_records",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId"), Index("timestamp")]
)
data class VitalRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val userId: String,
    
    // Heart Rate (BPM)
    val heartRate: Int? = null,
    
    // Blood Oxygen Saturation (%)
    val spO2: Int? = null,
    
    // Blood Pressure
    val systolicBp: Int? = null,
    val diastolicBp: Int? = null,
    
    // Respiratory Rate (breaths per minute)
    val respiratoryRate: Int? = null,
    
    // Body Temperature (Celsius)
    val temperatureCelsius: Float? = null,
    
    // Blood Glucose (mg/dL)
    val bloodGlucose: Float? = null,
    
    // Source: MANUAL, SENSOR, WEARABLE
    val source: String = "MANUAL",
    
    // Notes
    val notes: String? = null,
    
    // Timestamp
    val timestamp: Date = Date(),
    
    // Sync Status
    val isSynced: Boolean = false
)

/**
 * Enum for Vital Record Sources
 */
object VitalSource {
    const val MANUAL = "MANUAL"
    const val SENSOR = "SENSOR"
    const val WEARABLE = "WEARABLE"
    const val GOOGLE_FIT = "GOOGLE_FIT"
}
