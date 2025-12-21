package com.healthtracker.app.ui.trends

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.databinding.ActivityTrendsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Health Trends screen showing weekly and monthly health analytics.
 */
@AndroidEntryPoint
class TrendsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrendsBinding
    private val viewModel: TrendsViewModel by viewModels()
    
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrendsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupClickListeners()
        observeViewModel()
        
        firebaseAuth.currentUser?.uid?.let { userId ->
            viewModel.loadTrends(userId)
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener { finish() }
        
        binding.chipWeek.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setPeriod(TrendPeriod.WEEK)
        }
        
        binding.chipMonth.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setPeriod(TrendPeriod.MONTH)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    // Steps trend
                    binding.tvAvgSteps.text = String.format("%,d", state.avgSteps)
                    binding.tvStepsTrend.text = formatTrend(state.stepsTrend)
                    binding.tvStepsTrend.setTextColor(getTrendColor(state.stepsTrend))
                    
                    // Sleep trend
                    binding.tvAvgSleep.text = formatDuration(state.avgSleepMinutes)
                    binding.tvSleepTrend.text = formatTrend(state.sleepTrend)
                    binding.tvSleepTrend.setTextColor(getTrendColor(state.sleepTrend))
                    
                    // Heart rate
                    binding.tvAvgHeartRate.text = "${state.avgHeartRate} BPM"
                    binding.tvHeartRateTrend.text = formatTrend(state.heartRateTrend)
                    
                    // Calories
                    binding.tvAvgCalories.text = "${state.avgCaloriesBurned}"
                    binding.tvCaloriesTrend.text = formatTrend(state.caloriesTrend)
                    binding.tvCaloriesTrend.setTextColor(getTrendColor(state.caloriesTrend))
                    
                    // Hydration
                    binding.tvAvgHydration.text = "${state.avgHydration} glasses"
                }
            }
        }
    }

    private fun formatTrend(percentage: Int): String {
        return when {
            percentage > 0 -> "+$percentage%"
            percentage < 0 -> "$percentage%"
            else -> "No change"
        }
    }

    private fun getTrendColor(percentage: Int): Int {
        return when {
            percentage > 0 -> getColor(com.healthtracker.app.R.color.success_green)
            percentage < 0 -> getColor(com.healthtracker.app.R.color.alert_red)
            else -> getColor(com.healthtracker.app.R.color.text_secondary)
        }
    }

    private fun formatDuration(minutes: Int): String {
        val hours = minutes / 60
        val mins = minutes % 60
        return "${hours}h ${mins}m"
    }
}
