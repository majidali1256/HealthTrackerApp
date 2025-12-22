package com.healthtracker.app.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Document entity for storing medical documents like
 * lab results, prescriptions, and vaccination certificates.
 * Files are encrypted and stored locally with optional cloud sync.
 */
@Entity(
    tableName = "documents",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId"), Index("category")]
)
data class Document(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val userId: String,
    
    // Document details
    val title: String,
    val description: String? = null,
    
    // Category: LAB_RESULT, PRESCRIPTION, VACCINATION, IMAGING, OTHER
    val category: String,
    
    // File info
    val fileName: String,
    val filePath: String,
    val fileSize: Long,
    val mimeType: String,
    
    // Encryption
    val isEncrypted: Boolean = true,
    val encryptionKeyAlias: String? = null,
    
    // Cloud storage
    val cloudStorageUrl: String? = null,
    
    // Date on document (not upload date)
    val documentDate: Date? = null,
    
    // Provider/source
    val providerName: String? = null,
    
    // Tags for searchability
    val tags: String? = null,
    
    // Timestamps
    val uploadedAt: Date = Date(),
    
    // Sync Status
    val isSynced: Boolean = false
)

/**
 * Document Category Constants
 */
object DocumentCategory {
    const val LAB_RESULT = "LAB_RESULT"
    const val PRESCRIPTION = "PRESCRIPTION"
    const val VACCINATION = "VACCINATION"
    const val IMAGING = "IMAGING"
    const val INSURANCE = "INSURANCE"
    const val OTHER = "OTHER"
}
