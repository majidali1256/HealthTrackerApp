package com.healthtracker.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * User entity storing profile information and medical ID.
 * This is the core entity for user authentication and personalization.
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    
    val email: String,
    val displayName: String,
    val profilePictureUrl: String? = null,
    
    // Personal Info
    val dateOfBirth: Date? = null,
    val gender: String? = null,
    val heightCm: Float? = null,
    val weightKg: Float? = null,
    
    // Medical ID
    val bloodType: String? = null,
    val medicalId: String? = null,
    val allergies: String? = null,
    val medicalConditions: String? = null,
    val organDonor: Boolean = false,
    
    // Emergency Contacts (stored as JSON string for simplicity)
    val emergencyContacts: String? = null,
    
    // Timestamps
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    
    // Sync Status
    val isSynced: Boolean = false
)
