package com.healthtracker.app.ui.trends

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.R
import com.healthtracker.app.databinding.ActivityTrendsBinding
import com.healthtracker.app.ui.dashboard.DashboardActivity
import com.healthtracker.app.ui.profile.ProfileActivity
import com.healthtracker.app.ui.tools.ToolsActivity
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
        setupBottomNav()
        observeViewModel()
        
        firebaseAuth.currentUser?.uid?.let { userId ->
            viewModel.loadTrends(userId)
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener { 
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        
        binding.chipWeek.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setPeriod(TrendPeriod.WEEK)
        }
        
        binding.chipMonth.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.setPeriod(TrendPeriod.MONTH)
        }
    }

    private fun setupBottomNav() {
        binding.bottomNav.selectedItemId = R.id.nav_trends
        
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    overridePendingTransition(R.anim.nav_default_enter, R.anim.nav_default_exit)
                    true
                }
                R.id.nav_trends -> true // Already here
                R.id.nav_tools -> {
                    startActivity(Intent(this, ToolsActivity::class.java))
                    overridePendingTransition(R.anim.nav_default_enter, R.anim.nav_default_exit)
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    overridePendingTransition(R.anim.nav_default_enter, R.anim.nav_default_exit)
                    true
                }
                else -> false
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    // Steps trend
                    binding.tvAvgSteps.text = String.format("%,d", state.avgSteps)
                    binding.tvStepsTrend.text = formatTrend(state.stepsTrend)
                    
                    // Sleep trend
                    binding.tvAvgSleep.text = formatSleepDuration(state.avgSleepMinutes)
                    binding.tvSleepTrend.text = formatTrend(state.sleepTrend)
                    
                    // Heart rate trend
                    binding.tvAvgHeartRate.text = "${state.avgHeartRate} BPM"
                    binding.tvHeartRateTrend.text = formatTrend(state.heartRateTrend)
                    
                    // Hydration (no trend in state, use placeholder)
                    binding.tvAvgHydration.text = "${state.avgHydration} glasses"
                    binding.tvHydrationTrend.text = "+8%"
                }
            }
        }
    }

    private fun formatTrend(trend: Int): String {
        val sign = if (trend >= 0) "+" else ""
        return "$sign${trend}%"
    }

    private fun formatSleepDuration(minutes: Int): String {
        val hours = minutes / 60
        val mins = minutes % 60
        return "${hours}h ${mins}m"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
