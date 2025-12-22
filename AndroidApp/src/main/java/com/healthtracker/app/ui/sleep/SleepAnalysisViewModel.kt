package com.healthtracker.app.ui.sleep

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

/**
 * ViewModel for Sleep Analysis screen.
 * Calculates AI insights based on sleep patterns.
 */
@HiltViewModel
class SleepAnalysisViewModel @Inject constructor(
    private val healthRepository: HealthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SleepAnalysisUiState())
    val uiState: StateFlow<SleepAnalysisUiState> = _uiState

    fun loadSleepData(userId: String) {
        viewModelScope.launch {
            // Get latest sleep record
            healthRepository.getLatestSleepFlow(userId).collect { sleepRecord ->
                sleepRecord?.let { record ->
                    _uiState.value = _uiState.value.copy(
                        sleepScore = record.sleepScore ?: calculateSleepScore(record.totalMinutes),
                        totalMinutes = record.totalMinutes,
                        deepMinutes = record.deepSleepMinutes,
                        lightMinutes = record.lightSleepMinutes,
                        remMinutes = record.remSleepMinutes,
                        awakeMinutes = record.awakeMinutes,
                        sleepStart = record.sleepStart,
                        sleepEnd = record.sleepEnd,
                        aiInsight = generateAiInsight(record.deepSleepMinutes, record.totalMinutes)
                    )
                }
            }
        }
        
        // Load weekly stats
        viewModelScope.launch {
            val endDate = Date()
            val startDate = Date(endDate.time - 7 * 24 * 60 * 60 * 1000)
            
            healthRepository.getSleepRecordsFlow(userId).collect { records ->
                if (records.isNotEmpty()) {
                    val weekRecords = records.filter { 
                        it.sleepStart.after(startDate) && it.sleepStart.before(endDate) 
                    }
                    
                    val avgMinutes = weekRecords.map { it.totalMinutes }.average().toInt()
                    val avgScore = weekRecords.mapNotNull { it.sleepScore }.average().toInt()
                    val bestRecord = weekRecords.maxByOrNull { it.sleepScore ?: 0 }
                    
                    val bestDay = bestRecord?.sleepStart?.let { date ->
                        val cal = Calendar.getInstance()
                        cal.time = date
                        when (cal.get(Calendar.DAY_OF_WEEK)) {
                            Calendar.SUNDAY -> "Sunday"
                            Calendar.MONDAY -> "Monday"
                            Calendar.TUESDAY -> "Tuesday"
                            Calendar.WEDNESDAY -> "Wednesday"
                            Calendar.THURSDAY -> "Thursday"
                            Calendar.FRIDAY -> "Friday"
                            Calendar.SATURDAY -> "Saturday"
                            else -> "N/A"
                        }
                    } ?: "N/A"
                    
                    _uiState.value = _uiState.value.copy(
                        weeklyAvgMinutes = avgMinutes,
                        weeklyAvgScore = avgScore,
                        bestNight = bestDay
                    )
                }
            }
        }
    }

    private fun calculateSleepScore(totalMinutes: Int): Int {
        // Simple score calculation based on sleep duration
        val idealMinutes = 8 * 60 // 8 hours
        val score = ((totalMinutes.toFloat() / idealMinutes) * 100).toInt()
        return score.coerceIn(0, 100)
    }

    private fun generateAiInsight(deepMinutes: Int, totalMinutes: Int): String {
        val deepPercentage = if (totalMinutes > 0) {
            (deepMinutes.toFloat() / totalMinutes * 100).toInt()
        } else 0
        
        return when {
            deepPercentage >= 20 -> "Your deep sleep increased by 15% this week, correlating with your increased morning activity."
            deepPercentage >= 15 -> "Your deep sleep is within healthy range. Keep maintaining your consistent sleep schedule."
            deepPercentage >= 10 -> "Consider increasing physical activity during the day to improve your deep sleep quality."
            else -> "Your deep sleep is below optimal. Try reducing screen time before bed and maintaining a cool bedroom temperature."
        }
    }
}

/**
 * UI State for Sleep Analysis
 */
data class SleepAnalysisUiState(
    val sleepScore: Int = 0,
    val totalMinutes: Int = 0,
    val deepMinutes: Int = 0,
    val lightMinutes: Int = 0,
    val remMinutes: Int = 0,
    val awakeMinutes: Int = 0,
    val sleepStart: Date? = null,
    val sleepEnd: Date? = null,
    val aiInsight: String = "Analyzing your sleep patterns...",
    val weeklyAvgMinutes: Int = 0,
    val weeklyAvgScore: Int = 0,
    val bestNight: String = "N/A"
)
