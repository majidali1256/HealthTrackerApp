package com.healthtracker.app.data.local.dao

import androidx.room.*
import com.healthtracker.app.data.local.entities.Document
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Document entity.
 * Provides operations for medical document storage.
 */
@Dao
interface DocumentDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(document: Document): Long
    
    @Update
    suspend fun update(document: Document)
    
    @Delete
    suspend fun delete(document: Document)
    
    @Query("SELECT * FROM documents WHERE id = :id")
    suspend fun getById(id: Long): Document?
    
    @Query("SELECT * FROM documents WHERE userId = :userId ORDER BY uploadedAt DESC")
    fun getAllByUserFlow(userId: String): Flow<List<Document>>
    
    @Query("SELECT * FROM documents WHERE userId = :userId AND category = :category ORDER BY uploadedAt DESC")
    fun getByCategoryFlow(userId: String, category: String): Flow<List<Document>>
    
    @Query("SELECT * FROM documents WHERE userId = :userId AND (title LIKE '%' || :query || '%' OR tags LIKE '%' || :query || '%') ORDER BY uploadedAt DESC")
    fun searchDocumentsFlow(userId: String, query: String): Flow<List<Document>>
    
    @Query("DELETE FROM documents WHERE id = :id")
    suspend fun deleteById(id: Long)
    
    @Query("DELETE FROM documents WHERE userId = :userId")
    suspend fun deleteAllByUser(userId: String)
    
    @Query("SELECT * FROM documents WHERE isSynced = 0")
    suspend fun getUnsyncedDocuments(): List<Document>
    
    @Query("UPDATE documents SET isSynced = :isSynced WHERE id = :id")
    suspend fun updateSyncStatus(id: Long, isSynced: Boolean)
    
    @Query("UPDATE documents SET cloudStorageUrl = :url WHERE id = :id")
    suspend fun updateCloudUrl(id: Long, url: String)
}
