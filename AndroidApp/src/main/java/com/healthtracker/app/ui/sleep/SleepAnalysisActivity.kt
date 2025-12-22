package com.healthtracker.app.ui.sleep

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.databinding.ActivitySleepAnalysisBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

/**
 * Sleep Analysis detail screen.
 * Shows sleep stages visualization and AI-driven insights.
 */
@AndroidEntryPoint
class SleepAnalysisActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepAnalysisBinding
    private val viewModel: SleepAnalysisViewModel by viewModels()
    
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    
    private val timeFormatter = SimpleDateFormat("h:mm a", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepAnalysisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupClickListeners()
        observeViewModel()
        
        firebaseAuth.currentUser?.uid?.let { userId ->
            viewModel.loadSleepData(userId)
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: SleepAnalysisUiState) {
        // Sleep Score
        binding.tvSleepScore.text = state.sleepScore.toString()
        binding.tvScoreStatus.text = when {
            state.sleepScore >= 85 -> "Optimal"
            state.sleepScore >= 70 -> "Good"
            state.sleepScore >= 50 -> "Fair"
            else -> "Poor"
        }
        
        // Duration
        val hours = state.totalMinutes / 60
        val minutes = state.totalMinutes % 60
        binding.tvSleepDuration.text = "${hours}h ${minutes}m sleep"
        
        // Update sleep stages bar weights
        updateStagesBar(state)
        
        // Update legend
        binding.tvDeepSleep.text = "Deep ${formatDuration(state.deepMinutes)}"
        binding.tvLightSleep.text = "Light ${formatDuration(state.lightMinutes)}"
        binding.tvRemSleep.text = "REM ${formatDuration(state.remMinutes)}"
        binding.tvAwake.text = "Awake ${formatDuration(state.awakeMinutes)}"
        
        // Time range
        state.sleepStart?.let {
            binding.tvSleepStart.text = timeFormatter.format(it)
        }
        state.sleepEnd?.let {
            binding.tvSleepEnd.text = timeFormatter.format(it)
        }
        
        // AI Insight
        binding.tvAiInsight.text = state.aiInsight
        
        // Weekly stats
        binding.tvAvgSleep.text = formatDuration(state.weeklyAvgMinutes)
        binding.tvAvgScore.text = state.weeklyAvgScore.toString()
        binding.tvBestNight.text = state.bestNight
    }

    private fun updateStagesBar(state: SleepAnalysisUiState) {
        val total = state.deepMinutes + state.lightMinutes + state.remMinutes + state.awakeMinutes
        if (total == 0) return
        
        val deepWeight = (state.deepMinutes.toFloat() / total * 100).toInt().coerceAtLeast(1)
        val lightWeight = (state.lightMinutes.toFloat() / total * 100).toInt().coerceAtLeast(1)
        val remWeight = (state.remMinutes.toFloat() / total * 100).toInt().coerceAtLeast(1)
        val awakeWeight = (state.awakeMinutes.toFloat() / total * 100).toInt().coerceAtLeast(1)
        
        (binding.viewDeepSleep.layoutParams as LinearLayout.LayoutParams).weight = deepWeight.toFloat()
        (binding.viewLightSleep.layoutParams as LinearLayout.LayoutParams).weight = lightWeight.toFloat()
        (binding.viewRemSleep.layoutParams as LinearLayout.LayoutParams).weight = remWeight.toFloat()
        (binding.viewAwake.layoutParams as LinearLayout.LayoutParams).weight = awakeWeight.toFloat()
        
        binding.sleepStagesBar.requestLayout()
    }

    private fun formatDuration(minutes: Int): String {
        return when {
            minutes >= 60 -> "${minutes / 60}h ${minutes % 60}m"
            else -> "${minutes}m"
        }
    }
}
