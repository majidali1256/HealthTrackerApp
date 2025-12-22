package com.healthtracker.app.ui.profile

import androidx.lifecycle.ViewModel
import com.healthtracker.app.data.repository.HealthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * ViewModel for Profile Setup screen.
 */
@HiltViewModel
class ProfileSetupViewModel @Inject constructor(
    private val healthRepository: HealthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProfileSetupUiState())
    val uiState: StateFlow<ProfileSetupUiState> = _uiState
}

data class ProfileSetupUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentStep: Int = 1,
    val totalSteps: Int = 3
)
