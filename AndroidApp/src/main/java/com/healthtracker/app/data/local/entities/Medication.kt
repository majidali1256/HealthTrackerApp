package com.healthtracker.app.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Medication entity for tracking medication schedules,
 * dosages, and refill alerts.
 */
@Entity(
    tableName = "medications",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class Medication(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val userId: String,
    
    // Medication details
    val name: String,
    val dosage: String,
    val dosageUnit: String,  // mg, ml, tablet, etc.
    
    // Frequency
    val frequency: String,  // DAILY, TWICE_DAILY, WEEKLY, AS_NEEDED
    
    // Schedule times (stored as comma-separated 24h times: "08:00,20:00")
    val scheduleTimes: String,
    
    // Instructions
    val instructions: String? = null,
    val takeWithFood: Boolean = false,
    
    // Duration
    val startDate: Date,
    val endDate: Date? = null,
    
    // Refill tracking
    val currentQuantity: Int = 0,
    val refillAt: Int = 5,  // Alert when quantity reaches this
    val refillDate: Date? = null,
    
    // Status
    val isActive: Boolean = true,
    
    // Timestamps
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    
    // Sync Status
    val isSynced: Boolean = false
) {
    // Helper property for the UI (calculated in ViewModel usually, but provided here for compatibility)
    @Ignore
    var nextDoseTime: Date? = null

    // Helper property to map currentQuantity to what the Adapter expects
    val quantityRemaining: Int
        get() = currentQuantity
}

/**
 * Medication Frequency Constants
 */
object MedicationFrequency {
    const val ONCE_DAILY = "ONCE_DAILY"
    const val TWICE_DAILY = "TWICE_DAILY"
    const val THREE_TIMES_DAILY = "THREE_TIMES_DAILY"
    const val FOUR_TIMES_DAILY = "FOUR_TIMES_DAILY"
    const val WEEKLY = "WEEKLY"
    const val AS_NEEDED = "AS_NEEDED"
}
