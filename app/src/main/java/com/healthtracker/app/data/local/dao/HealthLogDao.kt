package com.healthtracker.app.data.local.dao

import androidx.room.*
import com.healthtracker.app.data.local.entities.HealthLog
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Data Access Object for HealthLog entity.
 * Provides operations for nutrition, hydration, and symptom logging.
 */
@Dao
interface HealthLogDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(healthLog: HealthLog): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(healthLogs: List<HealthLog>)
    
    @Update
    suspend fun update(healthLog: HealthLog)
    
    @Delete
    suspend fun delete(healthLog: HealthLog)
    
    @Query("SELECT * FROM health_logs WHERE id = :id")
    suspend fun getById(id: Long): HealthLog?
    
    @Query("SELECT * FROM health_logs WHERE userId = :userId ORDER BY timestamp DESC")
    fun getAllByUserFlow(userId: String): Flow<List<HealthLog>>
    
    @Query("SELECT * FROM health_logs WHERE userId = :userId AND type = :type ORDER BY timestamp DESC")
    fun getByTypeFlow(userId: String, type: String): Flow<List<HealthLog>>
    
    @Query("SELECT * FROM health_logs WHERE userId = :userId AND timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getByDateRangeFlow(userId: String, startDate: Date, endDate: Date): Flow<List<HealthLog>>
    
    @Query("SELECT * FROM health_logs WHERE userId = :userId ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentLogsFlow(userId: String, limit: Int = 10): Flow<List<HealthLog>>
    
    @Query("SELECT * FROM health_logs WHERE userId = :userId AND type = :type AND timestamp BETWEEN :startDate AND :endDate")
    suspend fun getByTypeAndDateRange(userId: String, type: String, startDate: Date, endDate: Date): List<HealthLog>
    
    @Query("SELECT SUM(value) FROM health_logs WHERE userId = :userId AND type = :type AND timestamp BETWEEN :startDate AND :endDate")
    suspend fun getTotalValueByTypeAndDate(userId: String, type: String, startDate: Date, endDate: Date): Float?
    
    @Query("SELECT AVG(value) FROM health_logs WHERE userId = :userId AND type = :type AND timestamp BETWEEN :startDate AND :endDate")
    suspend fun getAverageByTypeAndDateRange(userId: String, type: String, startDate: Date, endDate: Date): Float?
    
    @Query("DELETE FROM health_logs WHERE userId = :userId")
    suspend fun deleteAllByUser(userId: String)
    
    @Query("SELECT * FROM health_logs WHERE isSynced = 0")
    suspend fun getUnsyncedLogs(): List<HealthLog>
    
    @Query("UPDATE health_logs SET isSynced = :isSynced WHERE id = :id")
    suspend fun updateSyncStatus(id: Long, isSynced: Boolean)
}
