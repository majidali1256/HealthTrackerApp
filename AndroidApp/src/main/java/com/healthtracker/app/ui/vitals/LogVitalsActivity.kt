package com.healthtracker.app.ui.vitals

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.healthtracker.app.databinding.ActivityLogVitalsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Activity for logging vital signs: heart rate, blood pressure, SpO2, glucose, temperature.
 * Saves data to local Room database and syncs to Firebase.
 */
@AndroidEntryPoint
class LogVitalsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogVitalsBinding
    private val viewModel: LogVitalsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogVitalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            saveVitals()
        }
    }

    private fun saveVitals() {
        val heartRate = binding.etHeartRate.text.toString().toIntOrNull()
        val systolic = binding.etSystolic.text.toString().toIntOrNull()
        val diastolic = binding.etDiastolic.text.toString().toIntOrNull()
        val spO2 = binding.etSpO2.text.toString().toIntOrNull()
        val glucose = binding.etGlucose.text.toString().toFloatOrNull()
        val temperature = binding.etTemperature.text.toString().toFloatOrNull()
        val notes = binding.etNotes.text.toString().takeIf { it.isNotBlank() }

        // At least one vital should be entered
        if (heartRate == null && systolic == null && spO2 == null && glucose == null && temperature == null) {
            Toast.makeText(this, "Please enter at least one vital reading", Toast.LENGTH_SHORT).show()
            return
        }

        // Validate ranges
        heartRate?.let {
            if (it < 30 || it > 220) {
                Toast.makeText(this, "Heart rate should be between 30-220 BPM", Toast.LENGTH_SHORT).show()
                return
            }
        }

        systolic?.let {
            if (it < 70 || it > 250) {
                Toast.makeText(this, "Systolic BP should be between 70-250 mmHg", Toast.LENGTH_SHORT).show()
                return
            }
        }

        spO2?.let {
            if (it < 50 || it > 100) {
                Toast.makeText(this, "SpO2 should be between 50-100%", Toast.LENGTH_SHORT).show()
                return
            }
        }

        viewModel.saveVitals(
            heartRate = heartRate,
            systolicBp = systolic,
            diastolicBp = diastolic,
            spO2 = spO2,
            bloodGlucose = glucose,
            temperatureCelsius = temperature?.let { (it - 32) * 5 / 9 }, // Convert F to C
            notes = notes
        )
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.saveState.collect { state ->
                when (state) {
                    is SaveState.Loading -> {
                        binding.btnSave.isEnabled = false
                        binding.progressBar.visibility = android.view.View.VISIBLE
                    }
                    is SaveState.Success -> {
                        binding.btnSave.isEnabled = true
                        binding.progressBar.visibility = android.view.View.GONE
                        Toast.makeText(this@LogVitalsActivity, "Vitals saved successfully!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    is SaveState.Error -> {
                        binding.btnSave.isEnabled = true
                        binding.progressBar.visibility = android.view.View.GONE
                        Toast.makeText(this@LogVitalsActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    is SaveState.Idle -> {
                        binding.btnSave.isEnabled = true
                        binding.progressBar.visibility = android.view.View.GONE
                    }
                }
            }
        }
    }
}

sealed class SaveState {
    object Idle : SaveState()
    object Loading : SaveState()
    object Success : SaveState()
    data class Error(val message: String) : SaveState()
}
