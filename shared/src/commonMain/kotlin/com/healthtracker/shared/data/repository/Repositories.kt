package com.healthtracker.shared.data.repository

import com.healthtracker.shared.data.model.*
import kotlinx.coroutines.flow.Flow

/**
 * Health data repository interface - implemented per platform
 */
interface HealthRepository {
    
    // User
    suspend fun saveUser(user: User)
    suspend fun getUser(userId: String): User?
    fun observeUser(userId: String): Flow<User?>
    
    // Vitals
    suspend fun saveVitalRecord(record: VitalRecord)
    suspend fun getVitalRecords(userId: String, type: VitalType, limit: Int = 50): List<VitalRecord>
    fun observeLatestVital(userId: String, type: VitalType): Flow<VitalRecord?>
    suspend fun getAverageVital(userId: String, type: VitalType, fromTimestamp: Long): Float?
    
    // Activity
    suspend fun saveActivityRecord(record: ActivityRecord)
    suspend fun getActivityForDate(userId: String, date: String): ActivityRecord?
    suspend fun updateSteps(userId: String, date: String, steps: Int)
    fun observeTodayActivity(userId: String): Flow<ActivityRecord?>
    suspend fun getActivityRange(userId: String, fromDate: String, toDate: String): List<ActivityRecord>
    
    // Sleep
    suspend fun saveSleepRecord(record: SleepRecord)
    suspend fun getSleepForDate(userId: String, date: String): SleepRecord?
    fun observeLatestSleep(userId: String): Flow<SleepRecord?>
    suspend fun getAverageSleep(userId: String, days: Int): Int?
    
    // Health Logs
    suspend fun saveHealthLog(log: HealthLog)
    suspend fun getHealthLogs(userId: String, type: HealthLogType?, limit: Int = 50): List<HealthLog>
    fun observeRecentLogs(userId: String, limit: Int = 10): Flow<List<HealthLog>>
    suspend fun getTodayHydration(userId: String): Int
    
    // Medications
    suspend fun saveMedication(medication: Medication)
    suspend fun getMedications(userId: String, activeOnly: Boolean = true): List<Medication>
    fun observeMedications(userId: String): Flow<List<Medication>>
    suspend fun updateMedicationPills(medicationId: String, remaining: Int)
}

/**
 * Authentication repository interface
 */
interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<User>
    suspend fun signUp(email: String, password: String, displayName: String): Result<User>
    suspend fun signOut()
    fun getCurrentUser(): User?
    fun isSignedIn(): Boolean
    fun observeAuthState(): Flow<User?>
}
