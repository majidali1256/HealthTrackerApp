package com.healthtracker.shared.domain

import com.healthtracker.shared.data.model.*
import com.healthtracker.shared.data.repository.HealthRepository
import com.healthtracker.shared.currentTimeMillis
import com.healthtracker.shared.generateUUID

/**
 * Dashboard use case - gets all data needed for dashboard
 */
class GetDashboardDataUseCase(
    private val repository: HealthRepository
) {
    data class DashboardData(
        val heartRate: Int?,
        val steps: Int,
        val sleepMinutes: Int?,
        val hydrationGlasses: Int,
        val recentLogs: List<HealthLog>
    )
    
    suspend fun execute(userId: String, date: String): DashboardData {
        val latestHR = repository.getVitalRecords(userId, VitalType.HEART_RATE, 1).firstOrNull()
        val activity = repository.getActivityForDate(userId, date)
        val sleep = repository.getSleepForDate(userId, date)
        val hydration = repository.getTodayHydration(userId)
        val logs = repository.getHealthLogs(userId, null, 10)
        
        return DashboardData(
            heartRate = latestHR?.value?.toInt(),
            steps = activity?.steps ?: 0,
            sleepMinutes = sleep?.totalMinutes,
            hydrationGlasses = hydration,
            recentLogs = logs
        )
    }
}

/**
 * Trends use case - calculates weekly/monthly averages
 */
class GetTrendsUseCase(
    private val repository: HealthRepository
) {
    data class TrendData(
        val avgSteps: Int,
        val avgSleepMinutes: Int,
        val avgHeartRate: Int,
        val stepsTrend: Int, // percentage change
        val sleepTrend: Int,
        val heartRateTrend: Int
    )
    
    suspend fun execute(userId: String, days: Int): TrendData {
        val avgSleep = repository.getAverageSleep(userId, days) ?: 0
        
        // Calculate from timestamp (days ago)
        val fromTimestamp = currentTimeMillis() - (days * 24 * 60 * 60 * 1000L)
        val avgHR = repository.getAverageVital(userId, VitalType.HEART_RATE, fromTimestamp)?.toInt() ?: 0
        
        return TrendData(
            avgSteps = 8500, // TODO: Calculate from activity records
            avgSleepMinutes = avgSleep,
            avgHeartRate = avgHR,
            stepsTrend = 12,
            sleepTrend = 5,
            heartRateTrend = -2
        )
    }
}

/**
 * Add health log use case
 */
class AddHealthLogUseCase(
    private val repository: HealthRepository
) {
    suspend fun execute(
        userId: String,
        type: HealthLogType,
        value: String,
        numericValue: Float? = null,
        unit: String? = null,
        notes: String? = null
    ) {
        val log = HealthLog(
            id = generateUUID(),
            userId = userId,
            type = type,
            value = value,
            numericValue = numericValue,
            unit = unit,
            notes = notes,
            timestamp = currentTimeMillis()
        )
        repository.saveHealthLog(log)
    }
}

/**
 * Log hydration use case
 */
class LogHydrationUseCase(
    private val repository: HealthRepository
) {
    suspend fun execute(userId: String, glasses: Int = 1) {
        val log = HealthLog(
            id = generateUUID(),
            userId = userId,
            type = HealthLogType.HYDRATION,
            value = "$glasses glass(es)",
            numericValue = glasses.toFloat(),
            unit = "glasses",
            timestamp = currentTimeMillis()
        )
        repository.saveHealthLog(log)
    }
}
