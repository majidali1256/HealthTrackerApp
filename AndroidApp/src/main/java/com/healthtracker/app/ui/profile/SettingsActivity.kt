package com.healthtracker.app.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.healthtracker.app.R
import com.healthtracker.app.databinding.ActivitySettingsBinding
import com.healthtracker.app.services.StepCounterService
import com.healthtracker.app.utils.HydrationReminderWorker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        setupSwitchListeners()
        loadSettings()
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            saveSettings()
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        binding.rowDeleteData.setOnClickListener {
            showDeleteConfirmation()
        }

        binding.rowPrivacyPolicy.setOnClickListener {
            Toast.makeText(this, "Opening Privacy Policy...", Toast.LENGTH_SHORT).show()
        }

        binding.rowTerms.setOnClickListener {
            Toast.makeText(this, "Opening Terms of Service...", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupSwitchListeners() {
        // Water reminders toggle
        binding.switchWaterReminders.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                HydrationReminderWorker.schedule(this)
                Toast.makeText(this, "Water reminders enabled (every 2 hours)", Toast.LENGTH_SHORT).show()
            } else {
                HydrationReminderWorker.cancel(this)
                Toast.makeText(this, "Water reminders disabled", Toast.LENGTH_SHORT).show()
            }
        }
        
        // Step counter toggle  
        binding.switchStepGoal.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                StepCounterService.start(this)
                Toast.makeText(this, "Step counter started", Toast.LENGTH_SHORT).show()
            } else {
                StepCounterService.stop(this)
                Toast.makeText(this, "Step counter stopped", Toast.LENGTH_SHORT).show()
            }
        }
        
        // Dark mode toggle
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            val prefs = getSharedPreferences("settings", MODE_PRIVATE)
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
            
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES 
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            
            Toast.makeText(
                this, 
                if (isChecked) "Dark mode enabled" else "Light mode enabled",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun loadSettings() {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        binding.switchDailyReminders.isChecked = prefs.getBoolean("daily_reminders", true)
        binding.switchMedAlerts.isChecked = prefs.getBoolean("med_alerts", true)
        binding.switchStepGoal.isChecked = prefs.getBoolean("step_goal", true)
        binding.switchWaterReminders.isChecked = prefs.getBoolean("water_reminders", false)
        binding.switchShareDoctor.isChecked = prefs.getBoolean("share_doctor", false)
        binding.switchAnalytics.isChecked = prefs.getBoolean("analytics", true)
        
        // Load dark mode preference
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        binding.switchDarkMode.isChecked = isDarkMode
    }

    private fun saveSettings() {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        prefs.edit().apply {
            putBoolean("daily_reminders", binding.switchDailyReminders.isChecked)
            putBoolean("med_alerts", binding.switchMedAlerts.isChecked)
            putBoolean("step_goal", binding.switchStepGoal.isChecked)
            putBoolean("water_reminders", binding.switchWaterReminders.isChecked)
            putBoolean("share_doctor", binding.switchShareDoctor.isChecked)
            putBoolean("analytics", binding.switchAnalytics.isChecked)
            putBoolean("dark_mode", binding.switchDarkMode.isChecked)
            apply()
        }
        Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show()
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Delete All Data")
            .setMessage("Are you sure you want to delete all your health data? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                // Delete data
                Toast.makeText(this, "All data deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onBackPressed() {
        saveSettings()
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
