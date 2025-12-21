package com.healthtracker.app.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main Dashboard screen with 60/40 biometric hub layout.
 * Top 60%: Primary metric and secondary metric cards
 * Bottom 40%: Scrollable recent health logs
 */
@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var healthLogAdapter: HealthLogAdapter
    
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
        
        // Load data
        firebaseAuth.currentUser?.uid?.let { userId ->
            viewModel.loadDashboardData(userId)
        }
    }
    
    private fun setupUI() {
        binding.tvGreeting.text = viewModel.getGreeting()
    }
    
    private fun setupRecyclerView() {
        healthLogAdapter = HealthLogAdapter()
        binding.rvRecentLogs.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            adapter = healthLogAdapter
        }
    }
    
    private fun setupClickListeners() {
        // FAB - Add new log
        binding.fabAddLog.setOnClickListener {
            showQuickLogDialog()
        }
        
        // Primary metric card - tap for details
        binding.cardPrimaryMetric.setOnClickListener {
            // Navigate to heart rate details
        }
        
        // Steps card
        binding.cardSteps.setOnClickListener {
            // Navigate to activity details
        }
        
        // Sleep card
        binding.cardSleep.setOnClickListener {
            // Navigate to sleep details
        }
        
        // Hydration card
        binding.cardHydration.setOnClickListener {
            // Quick add water
            // TODO: Implement quick hydration log
        }
        
        // View all logs
        binding.tvViewAll.setOnClickListener {
            // Navigate to all logs screen
        }
        
        // Profile picture
        binding.ivProfile.setOnClickListener {
            // Navigate to profile/settings
        }
        
        // Apply lift animation to cards
        applyLiftAnimation(binding.cardPrimaryMetric)
        applyLiftAnimation(binding.cardSteps)
        applyLiftAnimation(binding.cardSleep)
        applyLiftAnimation(binding.cardHydration)
    }
    
    /**
     * Applies lift animation to cards on touch.
     * Per UI spec: card scales up and adds shadow on hover/touch.
     */
    private fun applyLiftAnimation(view: View) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    v.animate()
                        .scaleX(1.02f)
                        .scaleY(1.02f)
                        .translationZ(8f)
                        .setDuration(100)
                        .start()
                }
                android.view.MotionEvent.ACTION_UP,
                android.view.MotionEvent.ACTION_CANCEL -> {
                    v.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .translationZ(0f)
                        .setDuration(100)
                        .start()
                    v.performClick()
                }
            }
            true
        }
    }
    
    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Observe UI State
                launch {
                    viewModel.uiState.collect { state ->
                        // User name
                        binding.tvUserName.text = state.userName
                        
                        // Heart rate
                        binding.tvPrimaryMetricValue.text = state.heartRate?.toString() ?: "--"
                        
                        // Steps
                        binding.tvStepsValue.text = formatNumber(state.steps)
                        val stepsProgress = ((state.steps.toFloat() / state.stepsGoal) * 100).toInt().coerceAtMost(100)
                        binding.progressSteps.progress = stepsProgress
                        
                        // Sleep
                        binding.tvSleepValue.text = state.sleepHours?.let { 
                            String.format("%.1fh", it) 
                        } ?: "--"
                        binding.tvSleepScore.text = state.sleepScore?.let { 
                            "Score: $it" 
                        } ?: "Score: --"
                        
                        // Hydration
                        binding.tvHydrationValue.text = state.hydrationGlasses.toString()
                    }
                }
                
                // Observe recent logs
                launch {
                    viewModel.recentLogs.collect { logs ->
                        healthLogAdapter.submitList(logs)
                        binding.emptyState.visibility = if (logs.isEmpty()) View.VISIBLE else View.GONE
                        binding.rvRecentLogs.visibility = if (logs.isEmpty()) View.GONE else View.VISIBLE
                    }
                }
            }
        }
    }
    
    private fun showQuickLogDialog() {
        // TODO: Implement bottom sheet for quick logging
    }
    
    private fun formatNumber(number: Int): String {
        return when {
            number >= 1000 -> String.format("%,d", number)
            else -> number.toString()
        }
    }
}
