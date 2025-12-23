package com.healthtracker.app.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthtracker.app.data.local.entities.*
import com.healthtracker.app.data.repository.HealthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Dashboard screen.
 * Provides real-time health metrics and recent logs.
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val healthRepository: HealthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState
    
    private val _recentLogs = MutableStateFlow<List<HealthLog>>(emptyList())
    val recentLogs: StateFlow<List<HealthLog>> = _recentLogs
    
    fun loadDashboardData(userId: String) {
        viewModelScope.launch {
            // Load user info
            healthRepository.getCurrentUserFlow().collect { user ->
                _uiState.value = _uiState.value.copy(
                    userName = user?.displayName ?: "User"
                )
            }
        }
        
        viewModelScope.launch {
            // Load latest vital record
            healthRepository.getLatestVitalFlow(userId).collect { vital ->
                _uiState.value = _uiState.value.copy(
                    heartRate = vital?.heartRate,
                    spO2 = vital?.spO2
                )
            }
        }
        
        viewModelScope.launch {
            // Load today's activity
            healthRepository.getTodayActivityFlow(userId).collect { activity ->
                _uiState.value = _uiState.value.copy(
                    steps = activity?.steps ?: 0,
                    stepsGoal = activity?.stepsGoal ?: 10000,
                    caloriesBurned = activity?.caloriesBurned ?: 0
                )
            }
        }
        
        viewModelScope.launch {
            // Load latest sleep
            healthRepository.getLatestSleepFlow(userId).collect { sleep ->
                sleep?.let {
                    val hours = it.totalMinutes / 60f
                    _uiState.value = _uiState.value.copy(
                        sleepHours = hours,
                        sleepScore = it.sleepScore
                    )
                }
            }
        }
        
        viewModelScope.launch {
            // Load recent logs
            healthRepository.getRecentHealthLogsFlow(userId, 10).collect { logs ->
                _recentLogs.value = logs
            }
        }
        
        viewModelScope.launch {
            // Load today's hydration
            val hydration = healthRepository.getTodayHydration(userId) ?: 0f
            _uiState.value = _uiState.value.copy(hydrationGlasses = hydration.toInt())
        }
    }
    
    fun getGreeting(): String {
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        return when {
            hour < 12 -> "Good Morning"
            hour < 17 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }
    
    /**
     * Add water glasses to today's hydration log
     */
    fun addWater(userId: String, glasses: Int) {
        viewModelScope.launch {
            try {
                val healthLog = HealthLog(
                    userId = userId,
                    type = HealthLogType.HYDRATION,
                    value = glasses.toFloat(),
                    unit = "glasses",
                    title = "Water intake",
                    description = "$glasses glass(es) of water"
                )
                healthRepository.addHealthLog(healthLog)
                // Refresh hydration display
                refreshHydration(userId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    /**
     * Add sleep record
     */
    fun addSleep(userId: String, hours: Float, score: Int? = null) {
        viewModelScope.launch {
            try {
                // Calculate sleep times (assume woke up now, slept 'hours' ago)
                val sleepEnd = java.util.Date()
                val calendar = java.util.Calendar.getInstance()
                calendar.time = sleepEnd
                calendar.add(java.util.Calendar.HOUR_OF_DAY, -(hours.toInt()))
                val sleepStart = calendar.time
                
                val sleepRecord = SleepRecord(
                    userId = userId,
                    sleepStart = sleepStart,
                    sleepEnd = sleepEnd,
                    totalMinutes = (hours * 60).toInt(),
                    sleepScore = score
                )
                healthRepository.addSleepRecord(sleepRecord)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    /**
     * Refresh hydration data after adding water
     */
    private fun refreshHydration(userId: String) {
        viewModelScope.launch {
            val hydration = healthRepository.getTodayHydration(userId) ?: 0f
            _uiState.value = _uiState.value.copy(hydrationGlasses = hydration.toInt())
        }
    }
}

/**
 * UI State for Dashboard
 */
data class DashboardUiState(
    val userName: String = "User",
    val heartRate: Int? = null,
    val spO2: Int? = null,
    val steps: Int = 0,
    val stepsGoal: Int = 10000,
    val caloriesBurned: Int = 0,
    val sleepHours: Float? = null,
    val sleepScore: Int? = null,
    val hydrationGlasses: Int = 0,
    val isLoading: Boolean = false
)
