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
        
        // Primary metric card - tap for heart rate details
        binding.cardPrimaryMetric.setOnClickListener {
            showHeartRateDialog()
        }
        
        // Steps card - go to Trends
        binding.cardSteps.setOnClickListener {
            startActivity(android.content.Intent(this, com.healthtracker.app.ui.trends.TrendsActivity::class.java))
            overridePendingTransition(com.healthtracker.app.R.anim.slide_in_right, com.healthtracker.app.R.anim.slide_out_left)
        }
        
        // Sleep card - Show add sleep dialog (long press for analysis screen)
        binding.cardSleep.setOnClickListener {
            showAddSleepDialog()
        }
        binding.cardSleep.setOnLongClickListener {
            startActivity(android.content.Intent(this, com.healthtracker.app.ui.sleep.SleepAnalysisActivity::class.java))
            overridePendingTransition(com.healthtracker.app.R.anim.slide_in_right, com.healthtracker.app.R.anim.slide_out_left)
            true
        }
        
        // Hydration card - Quick add water
        binding.cardHydration.setOnClickListener {
            showAddHydrationDialog()
        }
        
        // View all logs - go to Trends
        binding.tvViewAll.setOnClickListener {
            startActivity(android.content.Intent(this, com.healthtracker.app.ui.trends.TrendsActivity::class.java))
            overridePendingTransition(com.healthtracker.app.R.anim.slide_in_right, com.healthtracker.app.R.anim.slide_out_left)
        }
        
        // Profile picture - go to Profile
        binding.cardProfile.setOnClickListener {
            startActivity(android.content.Intent(this, com.healthtracker.app.ui.profile.ProfileActivity::class.java))
            overridePendingTransition(com.healthtracker.app.R.anim.slide_in_right, com.healthtracker.app.R.anim.slide_out_left)
        }
        
        // Apply lift animation to cards
        applyLiftAnimation(binding.cardPrimaryMetric)
        applyLiftAnimation(binding.cardSteps)
        applyLiftAnimation(binding.cardSleep)
        applyLiftAnimation(binding.cardHydration)
        
        // Bottom Navigation
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                com.healthtracker.app.R.id.nav_home -> true // Already on home
                com.healthtracker.app.R.id.nav_trends -> {
                    startActivity(android.content.Intent(this, com.healthtracker.app.ui.trends.TrendsActivity::class.java))
                    overridePendingTransition(com.healthtracker.app.R.anim.nav_default_enter, com.healthtracker.app.R.anim.nav_default_exit)
                    true
                }
                com.healthtracker.app.R.id.nav_tools -> {
                    startActivity(android.content.Intent(this, com.healthtracker.app.ui.tools.ToolsActivity::class.java))
                    overridePendingTransition(com.healthtracker.app.R.anim.nav_default_enter, com.healthtracker.app.R.anim.nav_default_exit)
                    true
                }
                com.healthtracker.app.R.id.nav_profile -> {
                    startActivity(android.content.Intent(this, com.healthtracker.app.ui.profile.ProfileActivity::class.java))
                    overridePendingTransition(com.healthtracker.app.R.anim.nav_default_enter, com.healthtracker.app.R.anim.nav_default_exit)
                    true
                }
                else -> false
            }
        }
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
        val options = arrayOf("❤️ Log Vitals", "💤 Log Sleep", "💧 Add Water", "🍎 Log Food", "💊 Medication", "📝 Symptom")
        com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
            .setTitle("Quick Log")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> startActivity(android.content.Intent(this, com.healthtracker.app.ui.vitals.LogVitalsActivity::class.java))
                    1 -> showAddSleepDialog()
                    2 -> showAddHydrationDialog()
                    3 -> startActivity(android.content.Intent(this, com.healthtracker.app.ui.food.FoodLoggerActivity::class.java))
                    4 -> startActivity(android.content.Intent(this, com.healthtracker.app.ui.medications.MedicationsActivity::class.java))
                    5 -> startActivity(android.content.Intent(this, com.healthtracker.app.ui.symptoms.SymptomCheckerActivity::class.java))
                }
            }
            .show()
    }
    
    private fun showHeartRateDialog() {
        val heartRate = viewModel.uiState.value.heartRate ?: 0
        com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
            .setTitle("❤️ Heart Rate Details")
            .setMessage("""
                Current: $heartRate BPM
                
                Status: ${if (heartRate in 60..100) "Normal" else if (heartRate > 100) "Elevated" else if (heartRate > 0) "Low" else "No data"}
                
                Resting heart rate for adults typically ranges from 60 to 100 beats per minute.
            """.trimIndent())
            .setPositiveButton("OK", null)
            .setNeutralButton("Log Vitals") { _, _ ->
                startActivity(android.content.Intent(this, com.healthtracker.app.ui.vitals.LogVitalsActivity::class.java))
            }
            .show()
    }
    
    private fun showAddHydrationDialog() {
        val glasses = arrayOf("1 glass", "2 glasses", "3 glasses", "4 glasses")
        com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
            .setTitle("💧 Add Water")
            .setItems(glasses) { _, which ->
                val amount = which + 1
                firebaseAuth.currentUser?.uid?.let { userId ->
                    viewModel.addWater(userId, amount)
                    android.widget.Toast.makeText(this, "Added $amount glass(es) of water", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
            .show()
    }
    
    private fun showAddSleepDialog() {
        val hours = arrayOf("4 hours", "5 hours", "6 hours", "7 hours", "8 hours", "9 hours", "10 hours")
        val hourValues = floatArrayOf(4f, 5f, 6f, 7f, 8f, 9f, 10f)
        
        com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
            .setTitle("💤 Log Last Night's Sleep")
            .setMessage("How many hours did you sleep?")
            .setItems(hours) { _, which ->
                val sleepHours = hourValues[which]
                firebaseAuth.currentUser?.uid?.let { userId ->
                    viewModel.addSleep(userId, sleepHours)
                    android.widget.Toast.makeText(
                        this, 
                        "Logged ${sleepHours.toInt()} hours of sleep", 
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNeutralButton("View Analysis") { _, _ ->
                startActivity(android.content.Intent(this, com.healthtracker.app.ui.sleep.SleepAnalysisActivity::class.java))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun formatNumber(number: Int): String {
        return when {
            number >= 1000 -> String.format("%,d", number)
            else -> number.toString()
        }
    }
}
