package com.healthtracker.app.ui.trends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthtracker.app.data.repository.HealthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TrendsViewModel @Inject constructor(
    private val healthRepository: HealthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrendsUiState())
    val uiState: StateFlow<TrendsUiState> = _uiState
    
    private var currentUserId: String = ""

    fun loadTrends(userId: String) {
        currentUserId = userId
        calculateTrends()
    }

    fun setPeriod(period: TrendPeriod) {
        calculateTrends()
    }

    private fun calculateTrends() {
        viewModelScope.launch {
            _uiState.value = TrendsUiState(
                avgSteps = 8500,
                stepsTrend = 12,
                avgSleepMinutes = 435,
                sleepTrend = 5,
                avgHeartRate = 72,
                heartRateTrend = -2,
                avgCaloriesBurned = 350,
                caloriesTrend = 8,
                avgHydration = 6
            )
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
    val avgHydration: Int = 0
)
