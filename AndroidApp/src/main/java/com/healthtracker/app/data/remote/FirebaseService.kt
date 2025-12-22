package com.healthtracker.app.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.healthtracker.app.data.local.entities.*
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Firebase service for cloud synchronization of health data.
 * Handles authentication state, Firestore sync, and file uploads.
 */
@Singleton
class FirebaseService @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    
    // Collection names
    companion object {
        private const val USERS_COLLECTION = "users"
        private const val HEALTH_LOGS_COLLECTION = "health_logs"
        private const val VITAL_RECORDS_COLLECTION = "vital_records"
        private const val SLEEP_RECORDS_COLLECTION = "sleep_records"
        private const val ACTIVITY_RECORDS_COLLECTION = "activity_records"
        private const val MEDICATIONS_COLLECTION = "medications"
        private const val DOCUMENTS_COLLECTION = "documents"
    }
    
    // Auth
    val currentUser get() = auth.currentUser
    val isLoggedIn get() = auth.currentUser != null
    
    // User Sync
    suspend fun syncUser(user: User): Result<Unit> = runCatching {
        firestore.collection(USERS_COLLECTION)
            .document(user.id)
            .set(user)
            .await()
    }
    
    suspend fun fetchUser(userId: String): Result<User?> = runCatching {
        firestore.collection(USERS_COLLECTION)
            .document(userId)
            .get()
            .await()
            .toObject(User::class.java)
    }
    
    // Health Logs Sync
    suspend fun syncHealthLog(userId: String, healthLog: HealthLog): Result<String> = runCatching {
        val docRef = firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(HEALTH_LOGS_COLLECTION)
            .document()
        docRef.set(healthLog).await()
        docRef.id
    }
    
    suspend fun fetchHealthLogs(userId: String): Result<List<HealthLog>> = runCatching {
        firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(HEALTH_LOGS_COLLECTION)
            .get()
            .await()
            .toObjects(HealthLog::class.java)
    }
    
    // Vital Records Sync
    suspend fun syncVitalRecord(userId: String, vitalRecord: VitalRecord): Result<String> = runCatching {
        val docRef = firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(VITAL_RECORDS_COLLECTION)
            .document()
        docRef.set(vitalRecord).await()
        docRef.id
    }
    
    suspend fun fetchVitalRecords(userId: String): Result<List<VitalRecord>> = runCatching {
        firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(VITAL_RECORDS_COLLECTION)
            .get()
            .await()
            .toObjects(VitalRecord::class.java)
    }
    
    // Document Upload
    suspend fun uploadDocument(userId: String, file: File, mimeType: String): Result<String> = runCatching {
        val fileName = "${UUID.randomUUID()}_${file.name}"
        val storageRef = storage.reference
            .child("$DOCUMENTS_COLLECTION/$userId/$fileName")
        
        storageRef.putFile(android.net.Uri.fromFile(file)).await()
        storageRef.downloadUrl.await().toString()
    }
    
    suspend fun deleteDocument(cloudUrl: String): Result<Unit> = runCatching {
        storage.getReferenceFromUrl(cloudUrl).delete().await()
    }
    
    // Batch Sync (for offline data)
    suspend fun syncAllPendingData(
        userId: String,
        healthLogs: List<HealthLog>,
        vitalRecords: List<VitalRecord>,
        sleepRecords: List<SleepRecord>,
        activityRecords: List<ActivityRecord>,
        medications: List<Medication>
    ): Result<Unit> = runCatching {
        val batch = firestore.batch()
        
        healthLogs.forEach { log ->
            val ref = firestore.collection(USERS_COLLECTION)
                .document(userId)
                .collection(HEALTH_LOGS_COLLECTION)
                .document()
            batch.set(ref, log)
        }
        
        vitalRecords.forEach { record ->
            val ref = firestore.collection(USERS_COLLECTION)
                .document(userId)
                .collection(VITAL_RECORDS_COLLECTION)
                .document()
            batch.set(ref, record)
        }
        
        sleepRecords.forEach { record ->
            val ref = firestore.collection(USERS_COLLECTION)
                .document(userId)
                .collection(SLEEP_RECORDS_COLLECTION)
                .document()
            batch.set(ref, record)
        }
        
        activityRecords.forEach { record ->
            val ref = firestore.collection(USERS_COLLECTION)
                .document(userId)
                .collection(ACTIVITY_RECORDS_COLLECTION)
                .document()
            batch.set(ref, record)
        }
        
        medications.forEach { medication ->
            val ref = firestore.collection(USERS_COLLECTION)
                .document(userId)
                .collection(MEDICATIONS_COLLECTION)
                .document()
            batch.set(ref, medication)
        }
        
        batch.commit().await()
    }
}
