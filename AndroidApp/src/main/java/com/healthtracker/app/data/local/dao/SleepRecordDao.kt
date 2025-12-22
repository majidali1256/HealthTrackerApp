package com.healthtracker.app.data.local.dao

import androidx.room.*
import com.healthtracker.app.data.local.entities.SleepRecord
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Data Access Object for SleepRecord entity.
 * Provides operations for sleep tracking and analysis.
 */
@Dao
interface SleepRecordDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sleepRecord: SleepRecord): Long
    
    @Update
    suspend fun update(sleepRecord: SleepRecord)
    
    @Delete
    suspend fun delete(sleepRecord: SleepRecord)
    
    @Query("SELECT * FROM sleep_records WHERE id = :id")
    suspend fun getById(id: Long): SleepRecord?
    
    @Query("SELECT * FROM sleep_records WHERE userId = :userId ORDER BY sleepStart DESC")
    fun getAllByUserFlow(userId: String): Flow<List<SleepRecord>>
    
    @Query("SELECT * FROM sleep_records WHERE userId = :userId ORDER BY sleepStart DESC LIMIT 1")
    fun getLatestFlow(userId: String): Flow<SleepRecord?>
    
    @Query("SELECT * FROM sleep_records WHERE userId = :userId ORDER BY sleepStart DESC LIMIT 1")
    suspend fun getLatest(userId: String): SleepRecord?
    
    @Query("SELECT * FROM sleep_records WHERE userId = :userId AND sleepStart BETWEEN :startDate AND :endDate ORDER BY sleepStart DESC")
    fun getByDateRangeFlow(userId: String, startDate: Date, endDate: Date): Flow<List<SleepRecord>>
    
    @Query("SELECT * FROM sleep_records WHERE userId = :userId AND sleepStart BETWEEN :startDate AND :endDate ORDER BY sleepStart DESC")
    suspend fun getByDateRange(userId: String, startDate: Date, endDate: Date): List<SleepRecord>
    
    @Query("SELECT AVG(totalMinutes) FROM sleep_records WHERE userId = :userId AND sleepStart BETWEEN :startDate AND :endDate")
    suspend fun getAverageSleepDuration(userId: String, startDate: Date, endDate: Date): Float?
    
    @Query("SELECT AVG(sleepScore) FROM sleep_records WHERE userId = :userId AND sleepScore IS NOT NULL AND sleepStart BETWEEN :startDate AND :endDate")
    suspend fun getAverageSleepScore(userId: String, startDate: Date, endDate: Date): Float?
    
    @Query("DELETE FROM sleep_records WHERE userId = :userId")
    suspend fun deleteAllByUser(userId: String)
    
    @Query("SELECT * FROM sleep_records WHERE isSynced = 0")
    suspend fun getUnsyncedRecords(): List<SleepRecord>
    
    @Query("UPDATE sleep_records SET isSynced = :isSynced WHERE id = :id")
    suspend fun updateSyncStatus(id: Long, isSynced: Boolean)
}
