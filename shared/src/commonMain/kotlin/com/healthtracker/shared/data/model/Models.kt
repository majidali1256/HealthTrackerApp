package com.healthtracker.shared.data.model

import kotlinx.serialization.Serializable

/**
 * User profile model - shared between Android and iOS
 */
@Serializable
data class User(
    val id: String,
    val email: String,
    val displayName: String,
    val dateOfBirth: String? = null,
    val gender: Gender? = null,
    val heightCm: Float? = null,
    val weightKg: Float? = null,
    val bloodType: String? = null,
    val allergies: String? = null,
    val medicalConditions: String? = null,
    val emergencyContactName: String? = null,
    val emergencyContactPhone: String? = null,
    val createdAt: Long = 0,
    val updatedAt: Long = 0
)

@Serializable
enum class Gender {
    MALE, FEMALE, OTHER
}

/**
 * Vital record for heart rate, SpO2, blood pressure
 */
@Serializable
data class VitalRecord(
    val id: String,
    val userId: String,
    val type: VitalType,
    val value: Float,
    val secondaryValue: Float? = null, // For blood pressure (diastolic)
    val unit: String,
    val timestamp: Long,
    val source: String = "manual"
)

@Serializable
enum class VitalType {
    HEART_RATE,
    BLOOD_PRESSURE,
    SPO2,
    TEMPERATURE,
    BLOOD_GLUCOSE,
    WEIGHT
}

/**
 * Activity record for steps, distance, calories
 */
@Serializable
data class ActivityRecord(
    val id: String,
    val userId: String,
    val date: String, // YYYY-MM-DD
    val steps: Int = 0,
    val distanceMeters: Float = 0f,
    val caloriesBurned: Int = 0,
    val activeMinutes: Int = 0
)

/**
 * Sleep record with stages
 */
@Serializable
data class SleepRecord(
    val id: String,
    val userId: String,
    val date: String,
    val bedtime: Long,
    val wakeTime: Long,
    val totalMinutes: Int,
    val deepSleepMinutes: Int = 0,
    val lightSleepMinutes: Int = 0,
    val remSleepMinutes: Int = 0,
    val awakeMinutes: Int = 0,
    val sleepScore: Int = 0
)

/**
 * Health log entry for nutrition, hydration, symptoms
 */
@Serializable
data class HealthLog(
    val id: String,
    val userId: String,
    val type: HealthLogType,
    val value: String,
    val numericValue: Float? = null,
    val unit: String? = null,
    val notes: String? = null,
    val timestamp: Long
)

@Serializable
enum class HealthLogType {
    NUTRITION,
    HYDRATION,
    SYMPTOM,
    EXERCISE,
    MEDICATION,
    MOOD
}

/**
 * Medication with schedule
 */
@Serializable
data class Medication(
    val id: String,
    val userId: String,
    val name: String,
    val dosage: String,
    val frequency: String,
    val times: List<String>, // HH:mm format
    val startDate: String,
    val endDate: String? = null,
    val remainingPills: Int? = null,
    val isActive: Boolean = true
)
