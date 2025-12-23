package com.healthtracker.app.ui.trends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthtracker.app.data.repository.HealthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TrendsViewModel @Inject constructor(
    private val healthRepository: HealthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrendsUiState())
    val uiState: StateFlow<TrendsUiState> = _uiState
    
    private var currentUserId: String = ""
    private var currentPeriod: TrendPeriod = TrendPeriod.WEEK

    fun loadTrends(userId: String) {
        currentUserId = userId
        calculateTrends()
    }

    fun setPeriod(period: TrendPeriod) {
        currentPeriod = period
        calculateTrends()
    }

    private fun calculateTrends() {
        if (currentUserId.isEmpty()) return
        
        viewModelScope.launch {
            try {
                val days = when (currentPeriod) {
                    TrendPeriod.WEEK -> 7
                    TrendPeriod.MONTH -> 30
                }
                
                val endDate = Date()
                val calendar = Calendar.getInstance()
                calendar.time = endDate
                calendar.add(Calendar.DAY_OF_YEAR, -days)
                val startDate = calendar.time
                
                // Get previous period for comparison
                val prevEndDate = startDate
                calendar.add(Calendar.DAY_OF_YEAR, -days)
                val prevStartDate = calendar.time
                
                // Fetch real data from repository
                val avgSteps = healthRepository.getAverageSteps(currentUserId, startDate, endDate)
                val prevAvgSteps = healthRepository.getAverageSteps(currentUserId, prevStartDate, prevEndDate)
                val stepsTrend = if (prevAvgSteps > 0) ((avgSteps - prevAvgSteps) * 100 / prevAvgSteps) else 0
                
                val avgSleep = healthRepository.getAverageSleep(currentUserId, startDate, endDate)
                val prevAvgSleep = healthRepository.getAverageSleep(currentUserId, prevStartDate, prevEndDate)
                val sleepTrend = if (prevAvgSleep > 0) ((avgSleep - prevAvgSleep) * 100 / prevAvgSleep) else 0
                
                val avgHeartRate = healthRepository.getAverageHeartRate(currentUserId, startDate, endDate)
                val prevAvgHeartRate = healthRepository.getAverageHeartRate(currentUserId, prevStartDate, prevEndDate)
                val heartRateTrend = if (prevAvgHeartRate > 0) ((avgHeartRate - prevAvgHeartRate) * 100 / prevAvgHeartRate) else 0
                
                val avgCalories = healthRepository.getAverageCaloriesBurned(currentUserId, startDate, endDate)
                val prevAvgCalories = healthRepository.getAverageCaloriesBurned(currentUserId, prevStartDate, prevEndDate)
                val caloriesTrend = if (prevAvgCalories > 0) ((avgCalories - prevAvgCalories) * 100 / prevAvgCalories) else 0
                
                val avgHydration = healthRepository.getAverageHydration(currentUserId, startDate, endDate).toInt()
                
                _uiState.value = TrendsUiState(
                    avgSteps = avgSteps,
                    stepsTrend = stepsTrend,
                    avgSleepMinutes = avgSleep,
                    sleepTrend = sleepTrend,
                    avgHeartRate = avgHeartRate,
                    heartRateTrend = heartRateTrend,
                    avgCaloriesBurned = avgCalories,
                    caloriesTrend = caloriesTrend,
                    avgHydration = avgHydration,
                    isLoading = false,
                    hasData = avgSteps > 0 || avgSleep > 0 || avgHeartRate > 0 || avgHydration > 0
                )
            } catch (e: Exception) {
                // If error, show empty state
                _uiState.value = TrendsUiState(
                    isLoading = false,
                    hasData = false
                )
            }
        }
    }
}

enum class TrendPeriod { WEEK, MONTH }

data class TrendsUiState(
    val avgSteps: Int = 0,
    val stepsTrend: Int = 0,
    val avgSleepMinutes: Int = 0,
    val sleepTrend: Int = 0,
    val avgHeartRate: Int = 0,
    val heartRateTrend: Int = 0,
    val avgCaloriesBurned: Int = 0,
    val caloriesTrend: Int = 0,
    val avgHydration: Int = 0,
    val isLoading: Boolean = true,
    val hasData: Boolean = false
)
