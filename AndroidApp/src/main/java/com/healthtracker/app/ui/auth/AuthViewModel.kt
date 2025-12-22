package com.healthtracker.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.healthtracker.app.data.repository.AuthRepository
import com.healthtracker.app.data.repository.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for authentication screens (Login, SignUp).
 * Handles form validation and auth operations.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    // Auth State
    val authState: StateFlow<AuthState> = authRepository.authState
    
    // UI State
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState
    
    // Events (one-time events like navigation, toasts)
    private val _events = MutableSharedFlow<AuthEvent>()
    val events: SharedFlow<AuthEvent> = _events
    
    // Email Sign In
    fun signInWithEmail(email: String, password: String) {
        if (!validateEmail(email) || !validatePassword(password)) return
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            authRepository.signInWithEmail(email, password)
                .onSuccess { user ->
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    
                    // Check if profile is complete
                    val isProfileComplete = authRepository.isProfileComplete()
                    if (isProfileComplete) {
                        _events.emit(AuthEvent.NavigateToDashboard)
                    } else {
                        _events.emit(AuthEvent.NavigateToProfileSetup)
                    }
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Login failed"
                    )
                }
        }
    }
    
    // Email Sign Up
    fun signUpWithEmail(email: String, password: String, confirmPassword: String) {
        if (!validateEmail(email)) return
        if (!validatePassword(password)) return
        if (password != confirmPassword) {
            _uiState.value = _uiState.value.copy(confirmPasswordError = "Passwords don't match")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            authRepository.signUpWithEmail(email, password)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _events.emit(AuthEvent.NavigateToProfileSetup)
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Sign up failed"
                    )
                }
        }
    }
    
    // Google Sign In
    fun signInWithGoogle(account: GoogleSignInAccount) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            authRepository.signInWithGoogle(account)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    
                    val isProfileComplete = authRepository.isProfileComplete()
                    if (isProfileComplete) {
                        _events.emit(AuthEvent.NavigateToDashboard)
                    } else {
                        _events.emit(AuthEvent.NavigateToProfileSetup)
                    }
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Google sign in failed"
                    )
                }
        }
    }
    
    // Facebook Sign In
    fun signInWithFacebook(token: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            authRepository.signInWithFacebook(token)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    
                    val isProfileComplete = authRepository.isProfileComplete()
                    if (isProfileComplete) {
                        _events.emit(AuthEvent.NavigateToDashboard)
                    } else {
                        _events.emit(AuthEvent.NavigateToProfileSetup)
                    }
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Facebook sign in failed"
                    )
                }
        }
    }
    
    // Password Reset
    fun sendPasswordReset(email: String) {
        if (!validateEmail(email)) return
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            authRepository.sendPasswordResetEmail(email)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _events.emit(AuthEvent.ShowMessage("Password reset email sent"))
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to send reset email"
                    )
                }
        }
    }
    
    // Validation
    private fun validateEmail(email: String): Boolean {
        return if (email.isBlank()) {
            _uiState.value = _uiState.value.copy(emailError = "Email is required")
            false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.value = _uiState.value.copy(emailError = "Invalid email format")
            false
        } else {
            _uiState.value = _uiState.value.copy(emailError = null)
            true
        }
    }
    
    private fun validatePassword(password: String): Boolean {
        return if (password.isBlank()) {
            _uiState.value = _uiState.value.copy(passwordError = "Password is required")
            false
        } else if (password.length < 8) {
            _uiState.value = _uiState.value.copy(passwordError = "Password must be at least 8 characters")
            false
        } else {
            _uiState.value = _uiState.value.copy(passwordError = null)
            true
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

/**
 * UI State for authentication screens
 */
data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
)

/**
 * One-time events for authentication
 */
sealed class AuthEvent {
    object NavigateToDashboard : AuthEvent()
    object NavigateToProfileSetup : AuthEvent()
    data class ShowMessage(val message: String) : AuthEvent()
}
