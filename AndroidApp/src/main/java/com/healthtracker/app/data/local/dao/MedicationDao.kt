package com.healthtracker.app.data.local.dao

import androidx.room.*
import com.healthtracker.app.data.local.entities.Medication
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Data Access Object for Medication entity.
 * Provides operations for medication tracking and reminders.
 */
@Dao
interface MedicationDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medication: Medication): Long
    
    @Update
    suspend fun update(medication: Medication)
    
    @Delete
    suspend fun delete(medication: Medication)
    
    @Query("SELECT * FROM medications WHERE id = :id")
    suspend fun getById(id: Long): Medication?
    
    @Query("SELECT * FROM medications WHERE userId = :userId ORDER BY name ASC")
    fun getAllByUserFlow(userId: String): Flow<List<Medication>>
    
    @Query("SELECT * FROM medications WHERE userId = :userId AND isActive = 1 ORDER BY name ASC")
    fun getActiveMedicationsFlow(userId: String): Flow<List<Medication>>
    
    @Query("SELECT * FROM medications WHERE userId = :userId AND isActive = 1 ORDER BY name ASC")
    suspend fun getActiveMedications(userId: String): List<Medication>
    
    @Query("SELECT * FROM medications WHERE userId = :userId AND currentQuantity <= refillAt AND isActive = 1")
    fun getMedicationsNeedingRefillFlow(userId: String): Flow<List<Medication>>
    
    @Query("UPDATE medications SET currentQuantity = currentQuantity - 1 WHERE id = :id AND currentQuantity > 0")
    suspend fun decrementQuantity(id: Long)
    
    @Query("UPDATE medications SET currentQuantity = :quantity WHERE id = :id")
    suspend fun updateQuantity(id: Long, quantity: Int)
    
    @Query("UPDATE medications SET isActive = :isActive WHERE id = :id")
    suspend fun setActive(id: Long, isActive: Boolean)
    
    @Query("DELETE FROM medications WHERE userId = :userId")
    suspend fun deleteAllByUser(userId: String)
    
    @Query("SELECT * FROM medications WHERE isSynced = 0")
    suspend fun getUnsyncedMedications(): List<Medication>
    
    @Query("UPDATE medications SET isSynced = :isSynced WHERE id = :id")
    suspend fun updateSyncStatus(id: Long, isSynced: Boolean)
}
