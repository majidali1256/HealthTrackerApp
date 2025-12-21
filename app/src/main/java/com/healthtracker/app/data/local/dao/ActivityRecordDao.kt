package com.healthtracker.app.data.local.dao

import androidx.room.*
import com.healthtracker.app.data.local.entities.ActivityRecord
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Data Access Object for ActivityRecord entity.
 * Provides operations for daily activity tracking (steps, calories, etc.).
 */
@Dao
interface ActivityRecordDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activityRecord: ActivityRecord): Long
    
    @Update
    suspend fun update(activityRecord: ActivityRecord)
    
    @Delete
    suspend fun delete(activityRecord: ActivityRecord)
    
    @Query("SELECT * FROM activity_records WHERE id = :id")
    suspend fun getById(id: Long): ActivityRecord?
    
    @Query("SELECT * FROM activity_records WHERE userId = :userId AND date = :date")
    suspend fun getByDate(userId: String, date: Date): ActivityRecord?
    
    @Query("SELECT * FROM activity_records WHERE userId = :userId AND date = :date")
    fun getByDateFlow(userId: String, date: Date): Flow<ActivityRecord?>
    
    @Query("SELECT * FROM activity_records WHERE userId = :userId ORDER BY date DESC LIMIT 1")
    fun getTodayFlow(userId: String): Flow<ActivityRecord?>
    
    @Query("SELECT * FROM activity_records WHERE userId = :userId ORDER BY date DESC")
    fun getAllByUserFlow(userId: String): Flow<List<ActivityRecord>>
    
    @Query("SELECT * FROM activity_records WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getByDateRangeFlow(userId: String, startDate: Date, endDate: Date): Flow<List<ActivityRecord>>
    
    @Query("SELECT * FROM activity_records WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    suspend fun getByDateRange(userId: String, startDate: Date, endDate: Date): List<ActivityRecord>
    
    @Query("SELECT SUM(steps) FROM activity_records WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalSteps(userId: String, startDate: Date, endDate: Date): Int?
    
    @Query("SELECT SUM(caloriesBurned) FROM activity_records WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalCaloriesBurned(userId: String, startDate: Date, endDate: Date): Int?
    
    @Query("SELECT AVG(steps) FROM activity_records WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getAverageSteps(userId: String, startDate: Date, endDate: Date): Float?
    
    @Query("UPDATE activity_records SET steps = :steps, lastUpdated = :lastUpdated WHERE id = :id")
    suspend fun updateSteps(id: Long, steps: Int, lastUpdated: Date)
    
    @Query("DELETE FROM activity_records WHERE userId = :userId")
    suspend fun deleteAllByUser(userId: String)
    
    @Query("SELECT * FROM activity_records WHERE isSynced = 0")
    suspend fun getUnsyncedRecords(): List<ActivityRecord>
    
    @Query("UPDATE activity_records SET isSynced = :isSynced WHERE id = :id")
    suspend fun updateSyncStatus(id: Long, isSynced: Boolean)
}
