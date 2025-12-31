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
    
    // Goals (could be loaded from settings)
    private val stepsGoal = 10000
    private val sleepGoalHours = 8f
    private val hydrationGoal = 8
    
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
                recalculateHealthScore()
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
                recalculateHealthScore()
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
                    recalculateHealthScore()
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
            recalculateHealthScore()
        }
    }
    
    /**
     * Calculate health score based on current metrics.
     * Score is out of 100 points:
     * - Steps: 25 points (based on goal progress)
     * - Sleep: 25 points (based on 8 hour goal)
     * - Hydration: 25 points (based on 8 glasses goal)
     * - Heart Rate: 25 points (normal range 60-100 BPM)
     */
    private fun recalculateHealthScore() {
        val state = _uiState.value
        var score = 0
        
        // Steps score (0-25 points)
        val stepsProgress = minOf(state.steps.toFloat() / stepsGoal, 1f)
        score += (stepsProgress * 25).toInt()
        
        // Sleep score (0-25 points)
        val sleepHours = state.sleepHours ?: 0f
        val sleepProgress = minOf(sleepHours / sleepGoalHours, 1f)
        score += (sleepProgress * 25).toInt()
        
        // Hydration score (0-25 points)
        val hydrationProgress = minOf(state.hydrationGlasses.toFloat() / hydrationGoal, 1f)
        score += (hydrationProgress * 25).toInt()
        
        // Heart rate score (0-25 points)
        val heartRate = state.heartRate
        score += when {
            heartRate == null -> 12 // No data, give half points
            heartRate in 60..100 -> 25 // Normal range
            heartRate in 50..59 || heartRate in 101..110 -> 18 // Slightly off
            heartRate in 40..49 || heartRate in 111..120 -> 10 // Concerning
            else -> 5 // Very abnormal
        }
        
        _uiState.value = _uiState.value.copy(healthScore = score)
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
                // Log error silently - UI will show stale data
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
                // Log error silently
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
            recalculateHealthScore()
        }
    }
}

/**
 * UI State for Dashboard
 */
data class DashboardUiState(
    val userName: String = "User",
    val healthScore: Int = 0,
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

