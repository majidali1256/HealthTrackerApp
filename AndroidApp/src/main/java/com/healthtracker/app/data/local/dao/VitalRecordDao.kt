package com.healthtracker.app.data.local.dao

import androidx.room.*
import com.healthtracker.app.data.local.entities.VitalRecord
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Data Access Object for VitalRecord entity.
 * Provides operations for biometric measurements.
 */
@Dao
interface VitalRecordDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vitalRecord: VitalRecord): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vitalRecords: List<VitalRecord>)
    
    @Update
    suspend fun update(vitalRecord: VitalRecord)
    
    @Delete
    suspend fun delete(vitalRecord: VitalRecord)
    
    @Query("SELECT * FROM vital_records WHERE id = :id")
    suspend fun getById(id: Long): VitalRecord?
    
    @Query("SELECT * FROM vital_records WHERE userId = :userId ORDER BY timestamp DESC")
    fun getAllByUserFlow(userId: String): Flow<List<VitalRecord>>
    
    @Query("SELECT * FROM vital_records WHERE userId = :userId ORDER BY timestamp DESC LIMIT 1")
    fun getLatestFlow(userId: String): Flow<VitalRecord?>
    
    @Query("SELECT * FROM vital_records WHERE userId = :userId ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatest(userId: String): VitalRecord?
    
    @Query("SELECT * FROM vital_records WHERE userId = :userId AND timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getByDateRangeFlow(userId: String, startDate: Date, endDate: Date): Flow<List<VitalRecord>>
    
    @Query("SELECT * FROM vital_records WHERE userId = :userId ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentRecordsFlow(userId: String, limit: Int = 10): Flow<List<VitalRecord>>
    
    @Query("SELECT AVG(heartRate) FROM vital_records WHERE userId = :userId AND heartRate IS NOT NULL AND timestamp BETWEEN :startDate AND :endDate")
    suspend fun getAverageHeartRate(userId: String, startDate: Date, endDate: Date): Float?
    
    @Query("SELECT AVG(spO2) FROM vital_records WHERE userId = :userId AND spO2 IS NOT NULL AND timestamp BETWEEN :startDate AND :endDate")
    suspend fun getAverageSpO2(userId: String, startDate: Date, endDate: Date): Float?
    
    @Query("DELETE FROM vital_records WHERE userId = :userId")
    suspend fun deleteAllByUser(userId: String)
    
    @Query("SELECT * FROM vital_records WHERE isSynced = 0")
    suspend fun getUnsyncedRecords(): List<VitalRecord>
    
    @Query("UPDATE vital_records SET isSynced = :isSynced WHERE id = :id")
    suspend fun updateSyncStatus(id: Long, isSynced: Boolean)
}
