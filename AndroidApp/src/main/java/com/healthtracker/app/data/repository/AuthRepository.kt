package com.healthtracker.app.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.healthtracker.app.data.local.dao.UserDao
import com.healthtracker.app.data.local.entities.User
import com.healthtracker.app.data.remote.FirebaseService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for authentication operations.
 * Handles email/password, Google, and Facebook sign-in.
 */
@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userDao: UserDao,
    private val firebaseService: FirebaseService
) {
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState
    
    val currentFirebaseUser: FirebaseUser? get() = firebaseAuth.currentUser
    
    init {
        // Listen for auth state changes
        firebaseAuth.addAuthStateListener { auth ->
            _authState.value = if (auth.currentUser != null) {
                AuthState.Authenticated(auth.currentUser!!)
            } else {
                AuthState.Unauthenticated
            }
        }
    }
    
    // Email/Password Sign In
    suspend fun signInWithEmail(email: String, password: String): Result<FirebaseUser> = runCatching {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        result.user ?: throw Exception("Sign in failed")
    }
    
    // Email/Password Sign Up
    suspend fun signUpWithEmail(email: String, password: String): Result<FirebaseUser> = runCatching {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user ?: throw Exception("Sign up failed")
        
        // Create local user record
        val localUser = User(
            id = user.uid,
            email = email,
            displayName = user.displayName ?: email.substringBefore("@"),
            createdAt = Date()
        )
        userDao.insert(localUser)
        firebaseService.syncUser(localUser)
        
        user
    }
    
    // Google Sign In
    suspend fun signInWithGoogle(account: GoogleSignInAccount): Result<FirebaseUser> = runCatching {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        signInWithCredential(credential)
    }
    
    // Facebook Sign In
    suspend fun signInWithFacebook(token: String): Result<FirebaseUser> = runCatching {
        val credential = FacebookAuthProvider.getCredential(token)
        signInWithCredential(credential)
    }
    
    private suspend fun signInWithCredential(credential: AuthCredential): FirebaseUser {
        val result = firebaseAuth.signInWithCredential(credential).await()
        val user = result.user ?: throw Exception("Sign in failed")
        
        // Check if user exists locally, if not create
        val existingUser = userDao.getUserById(user.uid)
        if (existingUser == null) {
            val localUser = User(
                id = user.uid,
                email = user.email ?: "",
                displayName = user.displayName ?: "",
                profilePictureUrl = user.photoUrl?.toString(),
                createdAt = Date()
            )
            userDao.insert(localUser)
            firebaseService.syncUser(localUser)
        }
        
        return user
    }
    
    // Password Reset
    suspend fun sendPasswordResetEmail(email: String): Result<Unit> = runCatching {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }
    
    // Sign Out
    fun signOut() {
        firebaseAuth.signOut()
        // Note: Should also clear local user data in a full implementation
    }
    
    // Check if profile is complete
    suspend fun isProfileComplete(): Boolean {
        val userId = currentFirebaseUser?.uid ?: return false
        val user = userDao.getUserById(userId)
        return user?.dateOfBirth != null && user.heightCm != null && user.weightKg != null
    }
    
    // Get current local user
    fun getCurrentUserFlow(): Flow<User?> = userDao.getCurrentUserFlow()
}

/**
 * Auth state sealed class
 */
sealed class AuthState {
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(val user: FirebaseUser) : AuthState()
}
