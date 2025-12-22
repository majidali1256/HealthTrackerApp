package com.healthtracker.app.data.repository

import com.healthtracker.app.data.local.dao.*
import com.healthtracker.app.data.local.entities.*
import com.healthtracker.app.data.remote.FirebaseService
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for health data operations.
 * Coordinates between local Room database and Firebase cloud storage.
 * Implements offline-first approach.
 */
@Singleton
class HealthRepository @Inject constructor(
    private val userDao: UserDao,
    private val healthLogDao: HealthLogDao,
    private val vitalRecordDao: VitalRecordDao,
    private val sleepRecordDao: SleepRecordDao,
    private val activityRecordDao: ActivityRecordDao,
    private val medicationDao: MedicationDao,
    private val documentDao: DocumentDao,
    private val firebaseService: FirebaseService
) {
    
    // ===== USER =====
    fun getCurrentUserFlow(): Flow<User?> = userDao.getCurrentUserFlow()
    
    suspend fun getCurrentUser(): User? = userDao.getCurrentUser()
    
    suspend fun saveUser(user: User) {
        userDao.insert(user)
        // Sync to cloud
        firebaseService.syncUser(user)
    }
    
    suspend fun updateUser(user: User) {
        userDao.update(user.copy(updatedAt = Date(), isSynced = false))
    }
    
    // ===== HEALTH LOGS =====
    fun getHealthLogsFlow(userId: String): Flow<List<HealthLog>> = 
        healthLogDao.getAllByUserFlow(userId)
    
    fun getRecentHealthLogsFlow(userId: String, limit: Int = 10): Flow<List<HealthLog>> =
        healthLogDao.getRecentLogsFlow(userId, limit)
    
    suspend fun addHealthLog(healthLog: HealthLog): Long {
        return healthLogDao.insert(healthLog)
    }
    
    suspend fun getTodayHydration(userId: String): Float? {
        val startOfDay = getStartOfDay()
        val endOfDay = getEndOfDay()
        return healthLogDao.getTotalValueByTypeAndDate(
            userId, HealthLogType.HYDRATION, startOfDay, endOfDay
        )
    }
    
    suspend fun getTodayCalories(userId: String): Float? {
        val startOfDay = getStartOfDay()
        val endOfDay = getEndOfDay()
        return healthLogDao.getTotalValueByTypeAndDate(
            userId, HealthLogType.NUTRITION, startOfDay, endOfDay
        )
    }
    
    // ===== VITAL RECORDS =====
    fun getVitalRecordsFlow(userId: String): Flow<List<VitalRecord>> =
        vitalRecordDao.getAllByUserFlow(userId)
    
    fun getLatestVitalFlow(userId: String): Flow<VitalRecord?> =
        vitalRecordDao.getLatestFlow(userId)
    
    suspend fun addVitalRecord(vitalRecord: VitalRecord): Long {
        return vitalRecordDao.insert(vitalRecord)
    }
    
    suspend fun getAverageHeartRate(userId: String, days: Int = 7): Float? {
        val endDate = Date()
        val startDate = Date(endDate.time - (days * 24 * 60 * 60 * 1000L))
        return vitalRecordDao.getAverageHeartRate(userId, startDate, endDate)
    }
    
    // ===== ACTIVITY RECORDS =====
    fun getTodayActivityFlow(userId: String): Flow<ActivityRecord?> =
        activityRecordDao.getTodayFlow(userId)
    
    suspend fun getTodayActivity(userId: String): ActivityRecord? {
        val today = getStartOfDay()
        return activityRecordDao.getByDate(userId, today)
    }
    
    suspend fun updateSteps(activityId: Long, steps: Int) {
        activityRecordDao.updateSteps(activityId, steps, Date())
    }
    
    suspend fun getOrCreateTodayActivity(userId: String): ActivityRecord {
        val today = getStartOfDay()
        return activityRecordDao.getByDate(userId, today)
            ?: ActivityRecord(userId = userId, date = today).also {
                activityRecordDao.insert(it)
            }
    }
    
    // ===== SLEEP RECORDS =====
    fun getSleepRecordsFlow(userId: String): Flow<List<SleepRecord>> =
        sleepRecordDao.getAllByUserFlow(userId)
    
    fun getLatestSleepFlow(userId: String): Flow<SleepRecord?> =
        sleepRecordDao.getLatestFlow(userId)
    
    suspend fun addSleepRecord(sleepRecord: SleepRecord): Long {
        return sleepRecordDao.insert(sleepRecord)
    }
    
    // ===== MEDICATIONS =====
    fun getActiveMedicationsFlow(userId: String): Flow<List<Medication>> =
        medicationDao.getActiveMedicationsFlow(userId)
    
    fun getMedicationsNeedingRefillFlow(userId: String): Flow<List<Medication>> =
        medicationDao.getMedicationsNeedingRefillFlow(userId)
    
    suspend fun addMedication(medication: Medication): Long {
        return medicationDao.insert(medication)
    }
    
    suspend fun takeMedication(medicationId: Long) {
        medicationDao.decrementQuantity(medicationId)
    }
    
    // ===== DOCUMENTS =====
    fun getDocumentsFlow(userId: String): Flow<List<Document>> =
        documentDao.getAllByUserFlow(userId)
    
    fun getDocumentsByCategoryFlow(userId: String, category: String): Flow<List<Document>> =
        documentDao.getByCategoryFlow(userId, category)
    
    suspend fun addDocument(document: Document): Long {
        return documentDao.insert(document)
    }
    
    // ===== SYNC =====
    suspend fun syncPendingData(userId: String) {
        val healthLogs = healthLogDao.getUnsyncedLogs()
        val vitalRecords = vitalRecordDao.getUnsyncedRecords()
        val sleepRecords = sleepRecordDao.getUnsyncedRecords()
        val activityRecords = activityRecordDao.getUnsyncedRecords()
        val medications = medicationDao.getUnsyncedMedications()
        
        firebaseService.syncAllPendingData(
            userId, healthLogs, vitalRecords, sleepRecords, activityRecords, medications
        ).onSuccess {
            // Mark all as synced
            healthLogs.forEach { healthLogDao.updateSyncStatus(it.id, true) }
            vitalRecords.forEach { vitalRecordDao.updateSyncStatus(it.id, true) }
            sleepRecords.forEach { sleepRecordDao.updateSyncStatus(it.id, true) }
            activityRecords.forEach { activityRecordDao.updateSyncStatus(it.id, true) }
            medications.forEach { medicationDao.updateSyncStatus(it.id, true) }
        }
    }
    
    // ===== HELPERS =====
    private fun getStartOfDay(): Date {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)
        return calendar.time
    }
    
    private fun getEndOfDay(): Date {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 23)
        calendar.set(java.util.Calendar.MINUTE, 59)
        calendar.set(java.util.Calendar.SECOND, 59)
        calendar.set(java.util.Calendar.MILLISECOND, 999)
        return calendar.time
    }
    
    // ===== TREND CALCULATIONS =====
    suspend fun getAverageSteps(userId: String, startDate: Date, endDate: Date): Int {
        val records = activityRecordDao.getByDateRange(userId, startDate, endDate)
        return if (records.isNotEmpty()) records.map { it.steps }.average().toInt() else 0
    }
    
    suspend fun getAverageSleep(userId: String, startDate: Date, endDate: Date): Int {
        val records = sleepRecordDao.getByDateRange(userId, startDate, endDate)
        return if (records.isNotEmpty()) records.map { it.totalMinutes }.average().toInt() else 0
    }
    
    suspend fun getAverageHeartRate(userId: String, startDate: Date, endDate: Date): Int {
        val avg = vitalRecordDao.getAverageHeartRate(userId, startDate, endDate)
        return avg?.toInt() ?: 0
    }
    
    suspend fun getAverageCaloriesBurned(userId: String, startDate: Date, endDate: Date): Int {
        val records = activityRecordDao.getByDateRange(userId, startDate, endDate)
        return if (records.isNotEmpty()) records.map { it.caloriesBurned }.average().toInt() else 0
    }
    
    suspend fun getAverageHydration(userId: String, startDate: Date, endDate: Date): Float {
        return healthLogDao.getAverageByTypeAndDateRange(
            userId, HealthLogType.HYDRATION, startDate, endDate
        ) ?: 0f
    }
    
    // ===== DOCUMENT OPERATIONS =====
    suspend fun uploadDocument(uri: android.net.Uri) {
        // Implementation would handle file upload to Firebase Storage
    }
    
    suspend fun deleteDocument(document: Document) {
        documentDao.delete(document)
        // Also delete from Firebase Storage
    }
    
    // ===== MEDICATION HELPERS =====
    suspend fun recordMedicationTaken(medicationId: Long) {
        medicationDao.decrementQuantity(medicationId)
    }
    
    suspend fun insertHealthLog(healthLog: HealthLog): Long {
        return healthLogDao.insert(healthLog)
    }
}
