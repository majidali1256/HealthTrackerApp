package com.healthtracker.app.data.local.dao

import androidx.room.*
import com.healthtracker.app.data.local.entities.User
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for User entity.
 * Provides CRUD operations and queries for user data.
 */
@Dao
interface UserDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)
    
    @Update
    suspend fun update(user: User)
    
    @Delete
    suspend fun delete(user: User)
    
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): User?
    
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserByIdFlow(userId: String): Flow<User?>
    
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?
    
    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getCurrentUser(): User?
    
    @Query("SELECT * FROM users LIMIT 1")
    fun getCurrentUserFlow(): Flow<User?>
    
    @Query("DELETE FROM users")
    suspend fun deleteAll()
    
    @Query("UPDATE users SET isSynced = :isSynced WHERE id = :userId")
    suspend fun updateSyncStatus(userId: String, isSynced: Boolean)
    
    @Query("SELECT * FROM users WHERE isSynced = 0")
    suspend fun getUnsyncedUsers(): List<User>
}
